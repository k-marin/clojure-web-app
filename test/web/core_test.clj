(ns web.core-test
  (:require [clojure.test :refer :all]
            [web.core :refer :all]
            [ring.mock.request :as mock]
            [compojure.core :refer [routes POST GET ANY]]))

(deftest a-test
  (testing "default test"
    (is (= 1 1))))

(def mock-request (POST "aaaaa" {}))

(defn mock-routing
  "Apply a list of routes to a Ring request map."
  [request & handlers]
  (println "mock routing invoked")
  (clojure.core/tap> request)
  (some #(% request) handlers))



(defn mock-routes
  "Create a Ring handler by combining several handlers into one."
  [& handlers]
  (println "mock routes invoked")
  (fn
    ([request]
     (clojure.core/tap> request)
     (apply mock-routing request handlers))
    ([request respond raise]
     (letfn [(f [handlers]
               (if (seq handlers)
                 (let [handler  (first handlers)
                       respond' #(if % (respond %) (f (rest handlers)))]
                   (handler request respond' raise))
                 (respond nil)))]
       (f handlers)))))



(defn mock-handler [{:keys [default-routes] :or {default-routes routes}}]
  (println "mock handler")
  (default-routes
   (GET "/" [:as req]
     (clojure.core/tap> req)
     {:status 200
      :headers {"Content-Type" "text/html"}
      :body "<h1> HI from root!!! </h1>"})
   (GET "/:user-name" [user-name :as req]
     (clojure.core/tap> req)
     {:status 200
      :headers {"Content-Type" "text/html"}
      :body (format "<h1> HI from  %s </h1>" user-name)})))

;; (deftest greet-test
;;   (testing "greet function should return Hello concatenated with given name"
;;     (is (greet "John") "Hello John")
;;     (is (not (= (greet "John") "Hello 666")))
;;     (is (greet "") "Hello ")))

(def handle (mock-handler mock-routes))

((mock-handler mock-routes) mock-request)


;; (deftest remove-trailing-slashes-test
;;   (testing "remove trailing slashes"
;;     (is (remove-trailing-slashes ((mock-handler mock-routes) mock-request)) 1)))

;; (remove-trailing-slashes-test)



(deftest your-handler-test
  (is (= (mock-handler (mock/request :get "/doc/10"))
         {:status  200
          :headers {"content-type" "text/plain"}
          :body    "Your expected result"})))

(deftest your-json-handler-test
  (is (= (mock-handler (-> (mock/request :post "/api/endpoint")
                           (mock/json-body {:foo "bar"})))
         {:status  201
          :headers {"content-type" "application/json"}
          :body    {:key "your expected result"}})))