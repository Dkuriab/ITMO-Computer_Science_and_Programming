sqr(A, B):- A >= B * B. 
divis(A, B):- sqr(A, B), 0 is mod(A, B), !.
divis(A, B):- sqr(A, B), B1 is B + 1, divis(A, B1).
prime(N):- N > 1, \+ composite(N).
composite(N) :-	divis(N, 2).

divisors(1, _, []):- !.
divisors(N, C, [C | R1]):-
	prime(C),
	0 is mod(N, C),
	N1 is div(N, C),
	divisors(N1, C, R1), !.	

divisors(N, C, R):-
	C1 is C + 1,
	divisors(N, C1, R).
	
prime_divisors(N, R):-
	number(N),
	divisors(N, 2, R), !.

normalized([], _, 1):- !.
normalized([H | Tail], Min, X):- 
	prime(H),
	H >= Min,
	normalized(Tail, H, X1), X is X1 * H, !.
	
prime_divisors(N, R):-
	normalized(R, 2, N), !.

get_n(0, R, R):- !.

get_n(N, X, R):-
	X1 is X + 1,
	prime(X1),
	N1 is N - 1,
	get_n(N1, X1, R), !.

get_n(N, X, R):-
	X1 is X + 1,	
	get_n(N, X1, R).

nth_prime(N, X):- get_n(N, 1, X).