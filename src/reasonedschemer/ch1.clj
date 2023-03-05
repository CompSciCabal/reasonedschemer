; vim: lispwords=run*,fresh:sw=2:
(ns reasonedschemer.ch1
  (:require
    [clojure.core.logic :refer [conde fresh fail run* s# u# == defrel]]
    [reasonedschemer.util :refer [defrel]]))


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

(run* [q]
  s# s#) ; (_0)

(run* [q]
  s# (== 'corn q)) ; (corn)

(run* [q]
  u# (== 'corn q)) ; ()

(run* [q]
  (== 'corn q)
  (== 'meal q)) ; ()

(run* [q]
  (== 'corn q)
  (== 'corn q)) ; (corn)

(run* [q]
  (conde [u#] [u#])) ; ()

(run* [q]
  (conde [(== 'olive q)] [u#])) ; (olive)

(run* [q]
  (conde
    [u#]
    [(== 'oil q)])) ; (oil)

(run* [q]
  (conde
    [(== 'olive q)]
    [(== 'oil q)])) ; (olive oil)

(run* [q]
  (fresh [x]
    (fresh [y]
      (conde
        [(== (list x y) q)]
        [(== (list y x) q)])))) ; ((_0 _1) (_0 _1))

(run* [q]
  (fresh [x]
    (fresh [y]
      (== (list x y) q)
      (== (list y x) q)))) ; ((_0 _0))

(run* [x]
  (conde
    [(== 'olive x) u#]
    [(== 'oil x)])) ; (oil)

(run* [x]
  (conde
    [(== 'olive x) s#]
    [(== 'oil x)])) ; (olive oil)

(run* [x]
  (conde
    [(== 'oil x)]
    [(== 'olive x) s#])) ; (oil olive)

(run* [x]
  (conde
    [(== 'virgin x) u#]
    [(conde
       [(== 'olive x)]
       [(conde
          [s#]
          [(== 'oil x)])])])) ; (olive _0 oil)

(run* [r]
  (fresh [x]
    (fresh [y]
      (== 'split x)
      (== 'pea y)
      (== (list x y) r)))) ; ((split pea))

(run* [r]
  (fresh [x y]
    (== 'split x)
    (== 'pea y)
    (== (list x y) r))) ; ((split pea))

(run* [r x y]
  (== 'split x)
  (== 'pea y)
  (== (list x y) r)) ; ([(split pea) split pea])

(run* [x y]
  (fresh [r]
    (== 'split x)
    (== 'pea y)
    (== (list x y) r))) ; ([split pea])

(run* [x y]
  (conde
    [(== 'split x) (== 'pea y)]
    [(== 'red x) (== 'bean y)])) ; ([split pea] [red bean])

(run* [r]
  (fresh [x y]
    (conde
      [(== 'split x) (== 'pea y)]
      [(== 'red x) (== 'bean y)])
    (== (list x y 'soup) r))) ; ((split pea soup) (red bean soup))

(defrel teacupo [t]
  (conde [(== 'tea t)] [(== 'cup t)]))

(run* [x]
  (teacupo x)) ; (tea cup)

(run* [x y]
  (conde
    [(teacupo x) (== true y)]
    [(== false x) (== true y)])) ; ([false true] [tea true] [cup true])

(run* [x y]
  (teacupo x)
  (teacupo y)) ; ([tea tea] [tea cup] [cup tea] [cup cup])

(run* [x y]
  (teacupo x)
  (teacupo x)) ; ([tea _0] [cup _0])

(run* [x y]
  (conde
    [(teacupo x) (teacupo x)]
    [(== false x) (teacupo y)])) ; ([false tea] [false cup] [tea _0] [cup _0])


;; Start on 2023-03-09 on frame 88

