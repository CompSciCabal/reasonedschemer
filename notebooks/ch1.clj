;; # Reasoned Schemer, Chapter 1
;;

^{:nextjournal.clerk/visibility {:code :hide}}
(ns ch1
  (:require
    [clojure.core.logic :refer [conde fresh fail run* s# u# ==]]))

;; Note that Clojure `core.logic` varies a bit from MiniKanren
;;
;; * `#s` and `#u` are `s#` and `u#`.
;;
;; * The first arg to `run*` should be a vector of symbols, i.e. use `(run* [q] ...)` instead of `(run* q ...)`.
;;
;; * There is no `conj2`.  `run` and `fresh` can take multiple clauses that are joined by conjunction (AND).
;;
;; * There is no `disj2`.  Use `conde` instead.  `conde` represents the disjunction (OR) of its clauses.
;;   Each clause in `conde` may in turn be a vector of constraints, joined by conjunction (AND).
;;
;; * Instead of Scheme quasiquotes, use `list` and quote literal symbols, e.g. instead
;;   of `` `(foo ,q)``, use `(list 'foo q)`.

(run* [q] u#)

(run* [q] fail)

(run* [q]
      (== 'pea 'pod))

(run* [q]
      (== q 'pea))

(run* [q]
      (== 'pea q))

(run* [q] s#)

(run* [q r]
       (== q (* 2 r))
       (== r 5))

(run* [q]
      (== 'pea 'pea))

(run* [q]
      (== q q))

(run* [q]
      (fresh [x]
             (== 'pea q)))

(run* [q]
      (fresh [x]
             (== 'pea x)))

(cons 1 '())

(cons 2 (cons 1 '()))

(run* [q]
      (fresh [x]
             (== (cons x '()) q)))

(run* [q]
      (fresh [x]
             (== (list x) q)))

(run* [q]
    (fresh [x]
          (== q (* 2 x))))

(run* [q]
      (fresh [x]
             (== x q)))

(run* [q]
      (fresh [x]
             (fresh [y]
                    (== (list y x y) q))))

;; First week ended with "The Second Law of ==" on page 24
