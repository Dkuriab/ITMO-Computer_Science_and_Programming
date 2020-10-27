"use strict";

// :NOTE: `Invalid unary (0 args)   : org.graalvm.polyglot.PolyglotException: ParseError: Invalid unary (0 args)` where exactly?
// Non user-friendly error messages
// :NOTE: wrong locations of errors
// :COMMENT: my errors shows the start of wrong operation

const abstractOperation = {};
abstractOperation.prototype = {
	evaluate : function() {
		const result = [];
		for (let f of this.operands) {
	    	result.push(f.evaluate(...arguments));
		}
		return this.operator(...result);
	},

	toString : function() {
		let toStr = "";
		for (let cur of this.operands) {
			toStr += cur.toString() + " ";
		}
		toStr += this.symbol;
		return toStr;
	},

	prefix : function() {
		let toStr = this.symbol;
		for (let cur of this.operands) {
			toStr += " " + cur.prefix();
		}
		return "(" + toStr + ")";
	}
}		

const abstractAtomic = {};
abstractAtomic.prototype = { 
	toString : function() { return this.info + ""; },
	prefix : function() { return this.info + ""; },
}

const v = ["x", "y", "z"];
function Atomic(evaluate) {
	function created(info) {
		this.info = (() => info)();
	}
	created.prototype = Object.create(abstractAtomic.prototype);
	created.prototype.evaluate = evaluate;
	return created;
}

const Variable = Atomic(function() {return arguments[v.indexOf(this.info)]});

const Const = Atomic(function() { return this.info; });

function Opertion(sign, operator) {
	function created() {
		this.operands = (() => arguments)();
	}
	created.prototype = Object.create(abstractOperation.prototype);
	created.prototype.operator = (() => operator)();
	created.prototype.symbol = (() => sign)();
	return created;
}

const Add = Opertion("+", (a, b) => (a + b));

const Subtract = Opertion("-", (a, b) => (a - b));

const Multiply = Opertion("*", (a, b) => (a * b));

const Divide = Opertion("/", (a, b) => (a / b));

const Negate = Opertion("negate", a => -a);

const Sinh = Opertion("sinh", a => Math.sinh(a));

const Cosh = Opertion("cosh", a => Math.cosh(a));


function ParseError(message, position) {
	this.message = message + " at position " + position;
}
ParseError.prototype.constructor = ParseError;
ParseError.prototype.name = "ParseError";

// :NOTE: too many code for parser

function isPartOfNumber(symbol) { return symbol >= '0' && symbol <= '9' || symbol === '-'; }
function isPartOfToken(symbol) { return symbol != ' ' && symbol != '(' && symbol != ')'; }
const replace = ["zero", "unary", "binary"];
const map = {
	"+": {"f": Add, "arg" : 2}, 
	"-": {"f": Subtract, "arg" : 2}, 
	"*": {"f": Multiply, "arg" : 2},
	"/": {"f": Divide, "arg" : 2},
	"negate": {"f": Negate, "arg" : 1},
	"sinh": {"f": Sinh, "arg" : 1},
	"cosh": {"f": Cosh, "arg" : 1}
};

function parsePrefix(expression) {
	if (expression.length == 0) { throw new ParseError("Empty input", ""); }

	let position = 0;

	function skipWS() {
		while (position < expression.length && expression.charAt(position) === ' ') {
			position++;
		}
	}

	function getWord() {
		let start = position;
		while (position < expression.length && isPartOfToken(expression.charAt(position))) {
			position++;
		}
		return expression.substring(start, position);
	}

	function parseFunction() {
		let word = getWord();
		if (map[word]) {
			return map[word];
		}

		if (word.length == 0 || word === ")") {
			throw new ParseError("Empty op", position);
		} else if (isPartOfNumber(word.charAt(0))) {
		 	return "Const op \"" + word + "\"";
		} else if ((word.charAt(0) >= 'a' && word.charAt(0) <= 'z') || (word.charAt(0) >= 'A' && word.charAt(0) <= 'Z')) {
			return "Variable op \"" + word + "\"";
		}
		throw new ParseError("Unknown operation: \"" + word + "\"", position);
	}

	function parseExpression() {
		skipWS();

		if (expression.charAt(position) == '(') {
			let start = position++;
			skipWS();
			let func = parseFunction();				
			let args = [];

			while(position < expression.length && expression.charAt(position) != ')') {
				args.push(parseExpression());
			}

			if (typeof func == "string") {
				throw new ParseError(func + " (" + args.length + " args)", start);
			} else if (args.length != func.arg) {
				throw new ParseError("Invalid " + replace[func.arg] + " (" + args.length + " args)", start);
			} else if (expression.charAt(position++) != ')') { 
				throw new ParseError("Missing ) for opened", + start);
			}

			skipWS();
			return new func.f(...args);

		} else if (expression.charAt(position) >= 'x' && expression.charAt(position) <= 'z') {	
		 	let pos = position++;
			skipWS();
			return new Variable(expression.charAt(pos));
		}

		let num = getWord();
		skipWS();
		if (!isNaN(num)) {
			return new Const(+num);
		} else if (typeof num != "string" || !isPartOfNumber(num.charAt(0))) { 
			throw new ParseError("Unknown variable: \"" + num + "\"", position);
		}
		throw new ParseError("Invalid number: \"" + num + "\"", position);
	}

	let exp = parseExpression();
	skipWS();
	if (position < expression.length) {
		throw new ParseError("Excessive info, stated", position);
	}
	return exp;
}