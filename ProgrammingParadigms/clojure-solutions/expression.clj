
(defn abstractOp [f] (fn [& operands] (fn [args] (apply f (mapv (fn [operand] (operand args)) operands)))))

(defn variable [name] (fn [args] (args name)))

(defn constant [value] (constantly value))

(def add (abstractOp +))

(def subtract (abstractOp -))

(def divide (abstractOp (fn [a b] (/ (double a) (double b)))))

(def multiply (abstractOp *))

(def pw (abstractOp #(Math/pow %1 %2)))

(def lg (abstractOp (fn [a b] (/ (Math/log (Math/abs b)) (Math/log (Math/abs a))))))

(def med (abstractOp (fn [& args] (nth (sort args) (int (/ (count args) 2))))))

(def avg (abstractOp (fn [& args] (/ (apply + args) (count args)))))

(defn negate [exp] (fn [args] (- (exp args))))

(def rep  {'+ add, '- subtract, '/ divide, '* multiply, 'negate negate, 'pw pw, 'lg lg, 'med med, 'avg avg})
(defn parseFunction [expression] ((fn parse [exp]
                                    (cond
                                      (list? exp) (apply (rep (nth exp 0)) (mapv parse (rest exp)))
                                      (number? exp) (constant exp)
                                      (symbol? exp) (variable (str exp))))
                                  (read-string expression)))

; 11 homework ------------------------------------------------------------------------------------------------------------

(declare Constant)
(declare *expression)
(declare Sqrt)
(defn proto-get [obj key]
           (cond
             (contains? obj key) (obj key)
             (contains? obj :prototype) (proto-get (obj :prototype) key)
             :else nil))
(defn proto-call [this key & args] (apply (proto-get this key) this args))
(defn method [key] (fn [this & args] (apply proto-call this key args)))
(defn field [key] (fn [this] (proto-get this key)))
(defn Constructor [ctor prototype] (fn [& args] (apply ctor {:prototype prototype} args)))

(def diff (method :diff))
(def diff-op (field :diff-op))
(def toString (method :toString))
(def toStringSuffix (method :toStringSuffix))
(def evaluate (method :evaluate))
(def leftOp (method :leftOp))
(def rightOp (method :rightOp))
(def args (field :args))
(def sign (field :sign))
(def operation (field :operation))
(def argument (method :argument))
(def argsA (field :argsA))

(comment ":NOTE: pre-define constants and use `ZERO`, `ONE`")
(def AtPrototype {
                  :argument #(argsA %)
                  :evaluate #(if (number? (argument %1))
                                          (argument %1)
                                          (%2 (argument %1)))
                  :toString #(if (number? (argument %))
                                  	       (format "%.1f" (argument %))
                                 	        (argument %))
                  :toStringSuffix toString
                  :diff     (fn [this var] (if (= (argument this) var) 
                  																																		(Constant 1) 
                  																																		(Constant 0)))
                  })
(defn Atomic [this argsA] (assoc this :argsA argsA))
(def AtomicConstructor (Constructor Atomic AtPrototype))

(def Variable AtomicConstructor)
(def Constant AtomicConstructor)
(def diffArgs (fn [var args] (mapv #(diff % var) args)))
(def OpPrototype {
                  :evaluate #(apply (operation %1) (mapv (fn [operand] (evaluate operand %2)) (args %1)))
                  :toString #(str "(" (sign %) (apply str (mapv (fn [x] (str " " (toString x))) (args %))) ")")
                  :toStringSuffix #(str "(" (apply str (mapv (fn [x] (str (toStringSuffix x) " ")) (args %))) (sign %) ")")
                  :diff (fn [this var] ((diff-op this) (args this) (diffArgs var (args this))))
                  })

(defn Operation [this diff sign operation] (fn [& args] (assoc this :operation operation :sign sign :diff-op diff :args args)))

(def AbstractOperation (Constructor Operation OpPrototype))

(comment ":NOTE: `first` and `second` is really not necessary here")
(def Add (AbstractOperation (fn [args dargs] (Add (first dargs) (second dargs)))
"+" +))

(def Subtract (AbstractOperation (fn [args dargs] (Subtract (first dargs) (second dargs)))
"-" -))

(def Multiply (AbstractOperation (fn [args dargs] (Add (Multiply (first dargs) 
																																																																	(second args)) 
																																																							(Multiply (first args) 
																																																																	(second dargs))))
"*" *))

(def Divide (AbstractOperation (fn [args dargs] (Divide (Subtract (Multiply (first dargs) 
																																																																												(second args)) 
																			                                        							(Multiply (first args) 	
																			                                        																	(second dargs))) 
																			                                    	(Multiply (second args) (second args))))
"/" #(/ (double %1) (double %2))))

(def Negate (AbstractOperation (fn [args dargs] (Negate (first dargs)))
"negate" -))

(def Square (AbstractOperation (fn [args dargs] (Multiply (Multiply (Constant 2) 
																																																																				(first args)) 
																																																										(first dargs)))
"square" #(Math/pow %1 2)))

(def Sqrt (AbstractOperation (fn [args dargs] (Multiply (Divide (first args)
					                                              													(Sqrt (Square (first args))))
					                                      													(Multiply (first dargs)
					                                                													(Divide (Constant 1)
																																																																										(Multiply (Constant 2) (Sqrt (first args)))))))
"sqrt" #(Math/sqrt (Math/abs %1))))

(def Pow (AbstractOperation () "**" #(Math/pow %1 %2))) 

(def Log (AbstractOperation () "//" #(/ (Math/log (Math/abs (double %2))) (Math/log (Math/abs (double %1))))))

(def replaceStr {"+" Add, "-" Subtract, "/" Divide, "*" Multiply, "negate" Negate, "sqrt" Sqrt, "square" Square, "**" Pow, "//" Log})
(defn parseObject [expression] ((fn parse [exp]
                                    (cond
                                      (list? exp) (apply (replaceStr (str (nth exp 0))) (mapv parse (rest exp)))
                                      (number? exp) (Constant exp)
                                      (symbol? exp) (Variable (str exp))))
                                    (read-string expression)))

; 12 homework ------------------------------------------------------------------------------------------------------------
(defn -return [value tail] {:value value :tail tail})
(def -valid? boolean)
(def -value :value)
(def -tail :tail)

(defn _show [result] (if (-valid? result) (str "-> " (pr-str (-value result)) " | " (pr-str (apply str (-tail result)))) "!"))
(defn tabulate [parser inputs](run! (fn [input] (printf "    %-10s %s\n" (pr-str input) (_show (parser input)))) inputs))
(defn _empty [value] (partial -return value))
(defn _char [p] (fn [[c & cs]] (if (and c (p c)) (-return c cs))))
(defn _map [f result] (if (-valid? result) (-return (f (-value result)) (-tail result))))
(defn _combine [f a b] (fn [str] (let [ar ((force a) str)] (if (-valid? ar) (_map (partial f (-value ar)) ((force b) (-tail ar)))))))
(defn _either [a b] (fn [str] (let [ar ((force a) str)] (if (-valid? ar) ar ((force b) str))))) 
(defn _parser [p] (fn [input] (-value ((_combine (fn [v _] v) p (_char #{\u0000})) (str input \u0000)))))

(defn +char [chars] (_char (set chars)))
(defn +map [f parser] (comp (partial _map f) parser))
(def +parser _parser)
(def +ignore (partial +map (constantly 'ignore)))
(defn iconj [coll value] (if (= value 'ignore) coll (conj coll value)))
(defn +seq [& ps] (reduce (partial _combine iconj) (_empty []) ps))
(defn +seqf [f & ps] (+map (partial apply f) (apply +seq ps)))
(defn +seqn [n & ps] (apply +seqf (fn [& vs] (nth vs n)) ps))
(defn +or [p & ps] (reduce _either p ps))
(defn +opt [p] (+or p (_empty nil)))
(defn +star [p] (letfn [(rec [] (+or (+seqf cons p (delay (rec))) (_empty ())))] (rec)))
(defn +plus [p] (+seqf cons p (+star p)))
(defn +str [p] (+map (partial apply str) p))

(def *space (+char " \t\n\r"))
(def *ws (+ignore (+star *space)))
(def *digit (+char "0123456789"))
(defn *wraped [p] (+seqn 1 *ws (+char "(") (+opt (+seqf cons *ws p (+star (+seqn 0 *ws p)))) *ws (+char ")")))
(def *all-chars (mapv char (range 32 128)))
(def *letter (+char (apply str (filter #(Character/isLetter %) *all-chars))))
; =========================================================================================================================
(def *number (+map (comp Constant read-string) (+str (+seq (+opt (+char "-")) (+str (+plus *digit)) (+char ".") *digit))))
(def *variable (+seqf Variable (+str (+plus (+char "xyz")))))
(comment ":NOTE: negate should be recognized as string, not as unordered set of characters")
(def *operation (+seqf #(replaceStr %) (+str (+plus (+char "+-/*nagte")))))
(def *atom (+or *number *variable))
(def *operands (+plus (+seqn 0 *ws (delay *expression) *ws)))

(def *exp (+seqf #(apply %2 %1)
        			*ws (+ignore (+char "("))
        			*operands	*operation
        			*ws (+ignore (+char ")")))) 

(def *expression (+or *exp *atom))

(def parseObjectSuffix (+parser (+seqn 0 *ws *expression *ws)))