(ns clj-fn.core)

(defn zws [v]
  (let [len (count v)
        vs (vec (sort v))
        p (int (/ len 2))]
    (case len
      0 0
      1 (vs 0)
      (if (odd? len)
        (vs p)
        (float (/ (+ (vs p)
                     (vs (- p 1)))
                  2))))))

   ;; (zws [2 5 1 7 6 3 9 8])

(defn incm [val key]
  (swap! val update-in [key] #(inc %)))

;; (def m (atom {:k1 0}))
;; (incm m :k1)
