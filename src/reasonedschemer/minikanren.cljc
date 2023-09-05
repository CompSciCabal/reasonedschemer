(ns reasonedschemer.minikanren)

;; This is a Clojure implementation of a mini-kanren engine based on The
;; Reasoned Schemer.


;; Like the book, a logic variable is represented by a symbol in a
;; vector.  We use `lvar` here to avoid a conflict with
;; `clojure.core/var`.

(defn lvar
  [sym]
  [sym])

(defn lvar?
  [x]
  (vector? x))


;; A _substitution_ is a mapping of logic variables to values. Each
;; value can be another logic variable, a constant value, or a list of
;; values.  The book uses a list of pairs to represent a substitution,
;; but in Clojure we can just use a map.

(def empty-s {})

(defn walk
  [v s]
  (if (contains? s v)
    (recur (get s v) s)
    v))

(defn occurs?
  "Recursively searches for lvar `x` within value `v`.
  "
  [x v s]
  (let [v (walk v s)]
    (cond
      (lvar? v) (= v x)
      (list? v) (some #(occurs? x % s) v))))

(defn ext-s
  [x v s]
  (when-not (occurs? x v s)
    (assoc s x v)))


(defn unify
  [u v s]
  (let [u (walk u s)
        v (walk v s)]
    (cond

      (= u v) s

      (lvar? u) (ext-s u v s)

      (lvar? v) (ext-s v u s)

      (and (list? u)
           (list? v))
      (let [s (unify (first u) (first v) s)]
        (and s (unify (rest u) (rest v) s))))))


;; A _stream_ is a sequence of substitutions and/or suspensions.
;;
;; Note that we use the Clojure sequence abstraction instead of
;; just a literal list.  For example, a Clojure list is a sequence,
;; but `cons` returns a sequence object that is not a list.
;;
;; A _suspension_ is a no-args function that returns a stream.
;;
;; A _goal_ is a function that takes a substitution and returns a stream if
;; successful or nil if not.
;;
;; A _relation_ is a function that returns a goal.
;;
;; === is a relation that unifies two values.

(defn ===
  [u v]
  (fn [s]
    (when-let [s (unify u v s)]
      (list s))))


;; Here are success and failure goals.  We use `ss` and `uu` because
;; `#s` and `#u` are not valid Clojure symbols.

(def ss (fn [s] (list s)))

(def uu (fn [_s] nil))


;; disj2 is a relation that combines two goals with a disjunction,
;; that is, the resulting goal succeeds if either or both sub-goals
;; succeed.
;;
;; We need to first define appendoo, which create a stream from two
;; sub-streams.
;;
;; We also need to define nil-or-empty?, since in clojure an empty
;; list is _not_ the same as nil.

(defn nil-or-empty?
  "An empty list in Clojure is _not_ the same as nil, so we have to check
  for either case where the book can just call (null? x)
  "
  [x]
  (or (nil? x)
      (and (sequential? x)
           (empty? x))))


(defn appendoo
  "Produces a stream that combines the streams `soo` and `too`.
  "
  [soo too]
  (cond
    (nil-or-empty? soo) too
    (sequential? soo)   (cons (first soo)
                              (appendoo (rest soo) too))
    :else               (fn []
                          (appendoo too (soo)))))

;; TODO stuck here. The `cons` on line 122 fails when the recursive
;; call to appendoo returns a function, but it appears the listing
;; in the book would do the same thing.

#_(appendoo [{(lvar 'a) 1}] [{(lvar 'b) 2}])
#_(appendoo [{(lvar 'a) 1}] (fn [] [{(lvar 'b) 2}]))


(defn disj2
  "Returns a goal that succeeds if either or both of goals `g1` or `g2`
  succeeds.
  "
  [g1 g2]
  (fn [s]
    (appendoo (g1 s) (g2 s))))


;; alwayso and nevero are relations that return an infinite sequence of
;; successes and failures, respectively. They are helpful for building other
;; relations, as we will see.


(defn alwayso
  []
  (fn [s]
    (fn []
      ((disj2 ss (alwayso)) s))))


(defn nevero
  []
  (fn [s]
    (fn []
      ((nevero) s))))


;; takeoo works like take, returning the first n items from a stream.

(defn takeoo
  "Takes the first `n` items from the stream `soo`.
  If `n` is falsey instead of a number, returns all items in `soo`.
  "
  [n soo]
  (cond
    (and n (zero? n))   nil
    (nil-or-empty? soo) nil
    (seq? soo)          (cons (first soo)
                              (takeoo (and n (dec n)) (rest soo)))
    :else               (takeoo n (soo))))
