(defproject lovelace "0.1.1"
            :description "A Clojure wrapper around Notion's API"
            :url "http://github.com/arthurbarroso/lovelace"
            :license {:name "MIT"
                      :url "https://opensource.org/licenses/MIT"}
            :dependencies [[org.clojure/clojure "1.10.1"]
                           [clj-http "3.10.0"]
                           [cheshire "5.10.0"]]
            :profiles {:socket {:jvm-opts ["-Dclojure.server.myrepl={:port,5555,:accept,clojure.core.server/repl}"]}}
            :repl-options {:init-ns lovelace.core})
