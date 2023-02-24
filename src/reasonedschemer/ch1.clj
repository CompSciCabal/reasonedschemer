; vim: lispwords=run*,fresh:sw=2:
(ns reasonedschemer.ch1
  (:require
    [clojure.core.logic :refer [conde fresh fail run* s# u# ==]]))

(run* [q] u#) ; ()

(run* [q] fail) ; ()

(run* [q]
  (== 'pea 'pod)) ; ()

(run* [q]
  (== q 'pea)) ; (pea)

(run* [q]
  (== 'pea q)) ; (pea)

(run* [q] s#) ; (_0)

(run* [q r]
  (== q (* 2 r))
  (== r 5))

(run* [q]
  (== 'pea 'pea)) ; (_0)

(run* [q]
  (== q q)) ; (_0)

(run* [q]
  (fresh [x]
    (== 'pea q))) ; (pea)

(run* [q]
  (fresh [x]
    (== 'pea x))) ; (_0)

(cons 1 '()) ; (1)

(cons 2 (cons 1 '())) ; (2 1)

(run* [q]
  (fresh [x]
    (== (cons x '()) q))) ; ((_0))

(run* [q]
  (fresh [x]
    (== (list x) q))) ; ((_0))

(run* [q]
  (fresh [x]
    (== q (* 2 x))))

(run* [q]
  (fresh [x]
    (== x q))) ; (_0)

(run* [q]
  (fresh [x]
    (fresh [y]
      (== (list y x y) q)))) ; ((_0 _1 _0))

;; First week ended with "The Second Law of ==" on page 24
