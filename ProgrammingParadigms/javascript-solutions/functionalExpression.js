"use strict";

function abstractOperation(operator) {
	return (...farg) => (...arg) => {
		const result = [];
    	for (let f of farg) {
        	result.push(f(...arg));
    	}
    	return operator(...result);
	} 
}

const variable = function(name) {
	let variables = {"x": 0, "y": 1, "z": 2};
	return (...arg) => arg[variables[name]];
}
const cnst = value => (...arg) => value;
const add = abstractOperation((a, b) => (a + b));
const subtract = abstractOperation((a, b) => (a - b));
const multiply = abstractOperation((a, b) => (a * b));
const divide = abstractOperation((a, b) => (a / b));
const negate = abstractOperation(a => -a);
const pi = cnst(Math.PI);
const e = cnst(Math.E);
const avg5 = abstractOperation((a1, a2, a3, a4, a5) => (a1 + a2 + a3 + a4 + a5) / 5);
const med3 = abstractOperation((a, b, c) => [a, b, c].sort((m, n) => (m - n))[1]);

let map = {
"+": 	  {"func" : add, "args": 2}, 
"-": 	  {"func" : subtract, "args": 2}, 
"*": 	  {"func" : multiply, "args": 2}, 
"/": 	  {"func" : divide, "args": 2}, 
"negate": {"func" : negate, "args": 1}, 
"pi":     {"func" : pi, "args": 0}, 
"e":      {"func" : e, "args": 0}, 
"med3":   {"func" : med3, "args": 3}, 
"avg5":   {"func" : avg5, "args": 5}
};

function parse(expression) {
	let stack = [];
	let stackSize = 0;
	let mass = expression.trim().split(" ");

	for (let s of mass) {
		s = s.trim();
		if (s === "") {
			continue;
		}
		if (!isNaN(s)) {
			stack[stackSize++] = cnst(+s);
		} else if (!map[s]) {
			stack[stackSize++] = variable(s);
		} else {
			let num = map[s].args;
			stack[stackSize - num] = num == 0 ? map[s].func : map[s].func(...stack.slice(stackSize - num, stackSize));
			stackSize = stackSize - num + 1; 
		}
	}
	return stack[0];
}