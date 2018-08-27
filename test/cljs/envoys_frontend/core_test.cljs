(ns envoys-frontend.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [envoys-frontend.core :as core]
            [envoys-frontend.backend :as backend]
            [envoys-frontend.views :as views]))

(def expected-nav-data
  [[:li {:class [""]} [:a {:href "#/"} "HOME"]]
   [:li {:class ["active"]} [:a {:href "#/about"} "ABOUT"]]
   [:li {:class [""]} [:a {:href "#/blog"} "BLOG"]]
   [:li {:class [""]} [:a {:href "#/contact"} "CONTACT"]]])

(def expected-hero-data
  [:div {:class "inner cover"}
   [:h1 {:class "cover-heading"} "test content"]
   [:p {:class "lead"} nil] nil])

(def expected-contact-panel)

(def expected-footer
  [:div {:class "mastfoot"}
   [:div {:class "inner"}
    [:p [:a {:href "https://github.com/envoylabs", :target "_blank"} "Github"]]
    [:p "Copyright 2018"]]])

;; instead of a partial
(def element-content #(nth % 2))

(deftest smoke-test
  (testing "smoke test"
    (is (= 1 1))))

(deftest panel-mapping-test
  (testing "panel mapping"
    (is (= "https://backend.envoys.io/about"
           (backend/panel->route :about-panel)))))

(deftest nav-list-items-test
  (testing "nav list items"
    (let [generated-nav (views/generate-nav-list-items :about-panel)]
      (is (= generated-nav
             expected-nav-data))
      (is (= (count generated-nav)
             4)))))

(deftest hero-container-test
  (testing "hero container"
    (let [generated-hero (views/hero-container {:title "test content"})]
      (is (= generated-hero
             expected-hero-data))
     (is (= (-> generated-hero
                element-content
                element-content)
            "test content")))))

(deftest footer-test
  (testing "footer"
    (is (= (-> (views/footer)
               element-content
               element-content
               second
               element-content)
           "Github"))))

(comment (deftest show-panel-test
   (testing "show panel"
     (is (= (first (views/show-panel :contact-panel))
            "test")))))
