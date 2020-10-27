(defn sameSize [& args] (every? (fn [arg] (== (count(first args)) (count arg))) args))
(defn fromNumbers [x] (every? number? x))
(defn numMatrix [m] (and (sameSize m) (every? fromNumbers m)))

(defn shaplessOp [f] (fn [& args] 
{:pre[(or (every? number? args) (and (every? vector? args) (sameSize args)))]}
																			(if (vector? (first args))
																									(apply mapv (shaplessOp f) args)
																									(apply f args))))

(def v+ (shaplessOp +))
(def v- (shaplessOp -))
(def v* (shaplessOp *))

(defn v*s [v, s] 
{:pre[(and (not= (count v) 0) (fromNumbers v) (number? s))]}
					(mapv (partial * s) v))

(defn scalar [x, y] 
{:pre[(and (not= (count x) 0) (not= (count y) 0) (fromNumbers x) (fromNumbers y))]}
					(apply + (v* x y)))

(defn vect [x, y] 
{:pre[(and (== (count x) 3) (== (count y) 3) (fromNumbers x) (fromNumbers y))]}		
																			(letfn [ 
																											(cross [i, j] (- (* (x i) (y j)) (* (x j) (y i))))
																										]
                   (vector (cross 1 2) (cross 2 0) (cross 0 1))))

(def m+ v+)
(def m- v-)
(def m* v*)

(defn transpose [m] 
{:pre[(numMatrix m)]}		
						(apply mapv vector m))

(defn m*s [m, s] 
{:pre[(and (numMatrix m) (number? s))]}
						(mapv (fn [x] (v*s x s)) m))

(defn m*v [m, v] 
{:pre[(and (numMatrix m) (fromNumbers v) (== (count (first m)) (count v)))]}
						(mapv (partial scalar v) m))

(defn m*m [a, b] 
{:pre[(and (numMatrix a) (numMatrix b) (== (count b) (count (transpose a))))]}
						(transpose (mapv (partial m*v a) (transpose b))))

(def s+ m+)
(def s- m-)
(def s* m*)