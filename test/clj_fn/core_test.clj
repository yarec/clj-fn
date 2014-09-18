(ns clj-fn.core-test
  (:require [clojure.test :refer :all]
            [clj-fn.core :refer :all]))

(deftest zws-test
  (testing "FIXME, I fail."
    (is (= 5.5 (zws [2 5 1 7 6 3 9 8])))))

(deftest incm-test
  (testing "FIXME, I fail."
    (let [m (atom {:k1 0})
          m1 (incm m :k1)]
      (is (= {:k1 1} m1)))
        ))
