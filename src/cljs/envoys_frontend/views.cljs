(ns envoys-frontend.views
  (:require
   [re-frame.core :as re-frame]
   [envoys-frontend.subs :as subs]
   [clojure.string :as string]))

(def logo-as-html
  [:span
   [:span {:style {"letter-spacing" ".3rem"}}
    "("]
   [:span {:style {"letter-spacing" ".3rem"}}
    "envoys"]
   [:span {:style {"color" "#fd5605"
                   "letter-spacing" ".3rem"}}
    "."]
   [:span {:style {"font-weight" "lighter"
                   "letter-spacing" ".5rem"}}
    "io"]
   [:span {:style {"letter-spacing" ".3rem"}}
    ")"]])

(def panels-list
  [:home-panel
   :about-panel
   :blog-panel
   :contact-panel])

(def vector-generator
  (repeat []))

(defn generate-nav-list-items [current-pane]
  (->> panels-list
       (map (fn [nav-item]
              (let [pane-name (string/replace (name nav-item)
                                              #"\-panel"
                                              "")
                    path (if (= pane-name
                                "home")
                           ""
                           pane-name)]
                [:li {:class [(if (= current-pane
                                     nav-item)
                                "active"
                                "")]}
                 [:a {:href (-> "#/"
                                (str path))}
                  (string/upper-case pane-name)]])))))

(defn nav []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    
    [:div {:class "masthead clearfix"}
     [:div {:class "inner"}
      [:h3 {:class "masthead-brand"
            :style {"margin-top" "0"}}
       logo-as-html]
      [:nav
       [:ul {:class "nav masthead-nav"}
        (generate-nav-list-items @active-panel)]]]]))

(defn hero-container [{title :title
                       hero-text :hero-text
                       btn-text :btn-text
                       btn-target-path :btn-target-path}]
  [:div {:class "inner cover"}
   [:h1 {:class "cover-heading"}
    title]
   [:p {:class "lead"}
    hero-text]
   (if (not= nil
             btn-target-path)
     [:p {:class "lead"}
      [:a {:href btn-target-path
           :class "btn btn-lg btn-default"}
       btn-text]])])

(defn footer []
  [:div {:class "mastfoot"}
   [:div {:class "inner"}
    [:p
     [:a {:href "https://github.com/envoylabs"
          :target "_blank"}
      "Github"]]
    [:p
     "Copyright 2018"]]])

;; home
(defn home-panel []
  [:div 
   (nav)
   (hero-container {:title logo-as-html
                    :hero-text "A functional-first engineering collective with a focus on doing things right, first time."
                    :btn-text "Learn more"
                    :btn-target-path "#/about"})
   (footer)])


;; about
(defn about-panel []
  [:div 
   (nav)
   (hero-container {:title "About"
                    :hero-text "We specialise in green-field and rapid prototyping work, from front-end and static MVPs to full-stack proof of concepts. Whether it's engineering, devops or business development, we can help with all areas of the project build and lifecycle."})
   (footer)])

;; blog
(defn blog-panel []
  [:div 
   (nav)
   (hero-container {:title "Blog"
                    :hero-text "Find out about our latest projects here."})
   (footer)])

;; contact
(defn contact-panel []
  [:div 
   (nav)
   (hero-container {:title "Contact us"
                    :hero-text "For specific requirements, contact our CTO on alex@lynh.am; for more general queries, drop us a line at hello@envoys.io"})
   (footer)])


;; main
(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    :blog-panel [blog-panel]
    :contact-panel [contact-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [show-panel @active-panel]))
