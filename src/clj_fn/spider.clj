(ns clj-fn.spider
  (:use clj-fn.time)
  (:require [clj-http.client :as client]
            [clj-http.cookies :as cookies]
            [clj-time.core :as time]
            )
  )

(def my-cs (cookies/cookie-store)) ; for removing that SID nonsense

(defn sayln
  "println that prints to stderr"
  [& stuff]
  (binding [*out* *err*]
    (apply println stuff)))

;; (download-with-cookie "http://www.baidu.com")
(defn download-with-cookie
  [a-link]
  (try (-> a-link (client/get {:cookie-store my-cs}))
       (catch Exception e nil)))

;; (download-bin-with-cookde "http://placehold.it/350x150" "/tmp/test-file.gif")
(defn download-bin-with-cookde [url outfile-name]
  (let [get-url (client/get url {:as :byte-array :cookie-store my-cs})]
    (with-open [w (clojure.java.io/output-stream outfile-name)]
      (.write w (:body get-url)))))

(defn download-bin [uri file]
  (with-open [in (clojure.java.io/input-stream uri)
              out (clojure.java.io/output-stream file)]
    (clojure.java.io/copy in out)))

(def *document-cache* (atom {}))

(defn download-cache-with-cookie
  [a-link]
  (if-not (@*document-cache* a-link)
    (do (let [document (download-with-cookie a-link)]
          (sayln :cache-miss)
          (swap! *document-cache*
                 merge
                 (reduce
                  (fn [acc x]
                    (merge acc {x document}))
                  {}
                  (:trace-redirects document)))
          document))

    (do (sayln :cache-hit)
        (@*document-cache* a-link))))



(defn dated-filename
  ([] (dated-filename "" ""))
  ([prefix suffix]
   (let [cur-time (time/now)
         d-str (time-str "yyyy-MM-dd-HH-mm")
         day (time/day cur-time)
         mon (time/month cur-time)
         yr  (time/year cur-time)
         hr  (time/hour cur-time)
         min (time/minute cur-time)]
     (str (if-not (= "" prefix) (str prefix "-") "")
          d-str suffix))))
