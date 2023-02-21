;; # Reasoned Schemer, Chapter 1
;;

^{:nextjournal.clerk/visibility {:code :hide}}
(ns ch1
  (:require
    [clojure.core.logic :refer [conde fresh run* s# u# ==]]))

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

