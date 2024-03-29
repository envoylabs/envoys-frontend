(ns envoys-frontend.views
  (:require
   [re-frame.core :as re-frame]
   [envoys-frontend.subs :as subs]
   [envoys-frontend.events :as events]
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
   :validators-panel
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
                    base-route "#/"
                    path (case pane-name
                           "home" base-route
                           "blog" "https://envoylabs.github.io/blog/"
                           (-> base-route (str pane-name)))]
                [:li {:class [(if (= current-pane
                                     nav-item)
                                "active"
                                "")]}
                 [:a {:href path}
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
                       btn-text :btn-text
                       btn-target-path :btn-target-path}]
  (let [hero-text (re-frame/subscribe [::subs/hero-text])]
    [:div {:class "inner cover"}
     [:h1 {:class "cover-heading"}
      title]
     [:p {:class "lead"}
      @hero-text]
     (if (not= nil
               btn-target-path)
       [:p {:class "lead"}
        [:a {:href btn-target-path
             :class "btn btn-default"}
         btn-text]])]))

(defn footer []
  [:div {:class "mastfoot"}
   [:div {:class "inner"}
    [:p
     [:a {:href "https://github.com/envoylabs"
          :target "_blank"}
      "Github"]]
    [:p
     "Copyright 2021"]
    [:p "Registered in England and Wales with Company No 12557870"]]])

;; home
(defn home-panel []
  [:div 
   (nav)
   (hero-container {:title logo-as-html
                    :btn-text "Learn more"
                    :btn-target-path "#/about"})
   (footer)])


;; about
(defn about-panel []
  [:div 
   (nav)
   (hero-container {:title "About"})
   [:div.inner {:style {"padding-top" "5px"}}
    [:p {:style {:font-weight "lighter"}}
     "We believe social skills are as important as engineering chops. We work hard to seamlessly integrate with and understand your culture and teams, enhancing them with a razor-sharp focus on delivering the right thing for your users."]
    [:p
     "We work at the cutting edge, and can help you shake off institutional inertia to deliver ideas and working code in areas like:"]
    [:p
     "Serverless applications | Data Engineering | Application development | Blockchain"]]
   (footer)])

;; validators
(defn validators-panel []
  [:div 
   (nav)
   (hero-container {:title "Needlecast Validators"})
   [:div.inner {:style {"padding-top" "5px"}}
    [:p {:style {:font-weight "lighter"}}
     "Envoys run several Proof-of-Stake Validator nodes for blockchains in the Cosmos ecosystem. These run under the Needlecast moniker."]
    [:p
     "We validate the Juno and Stargaze networks, and plan to add more soon."]
    [:p [:a {:href "https://www.mintscan.io/juno/validators/junovaloper17dn5e2n6w60pzyxeq79apr05r6jzfw7wgq3m07"
             :target "_blank"
             :style {"color" "#fd5605"}}
         "Stake on Juno"]]
    [:p [:a {:href "https://www.mintscan.io/stargaze/validators/starsvaloper17dn5e2n6w60pzyxeq79apr05r6jzfw7w7d8xrj"
             :target "_blank"
             :style {"color" "#fd5605"}}
         "Stake on Stargaze"]]
    [:p
     "We offer fair fees and best-in-class deployment and operational practices, born of our years of experience delivering business-critical applications at enterprise scale."]
    [:p "We also make tutorial videos focussed on Validating and writing Smart Contracts using CosmWasm. They can be found "
     [:a {:href "https://www.youtube.com/channel/UCrb3W9f3tP8d3XFyP7K9zrg"
          :target "_blank"
          :style {"color" "#fd5605"}}
      "here"]
     "."]
    [:a {:href "https://keybase.io/needlecast"
         :target "_blank"
         :style {"color" "#fd5605"}}
     "View Needlecast on Keybase"]

    [:p "We contribute to the core development of the Juno Network, and are a member of InterWasm."]]
   (footer)])


;; blog
(defn blog-panel []
  [:div 
   (nav)
   (hero-container {:title "Blog"})
   (footer)])

(defn html-label [for-element-id text]
  [:label {:for for-element-id} text])

(defn collect-contact-form [])

(defn contact-form [email-field message-field robots-field]
  [:form

   [:div.form-group
    (html-label "your-email-input" "Contact Email Address")
    [:input {:type "email"
             :class "form-control"
             :id "your-email-input"
             :aria-describedby "email-help"
             :placeholder "Enter your email..."
             :value (if email-field
                      email-field
                      "")
             :on-change #(re-frame/dispatch [::events/set-email-field-value (-> %
                                                                                .-target
                                                                                .-value)])}]
    [:small {:id "email-help"
             :class "form-text text-muted"}
     "We'll never share your email with anyone else."]]

   [:div.form-group
    (html-label "contact-message" "Your message")
    [:textarea {:class "form-control"
                :id "contact-message"
                :rows "3"
                :placeholder "Enter your message. We'll get back to you as soon as we can!"
                :value (if message-field
                         message-field
                         "")
                :on-change #(re-frame/dispatch [::events/set-message-field-value (-> %
                                                                                     .-target
                                                                                     .-value)])}]]

   [:div.form-group
    (html-label "robot-check" "Robot check: what is 3 x 7?")
    [:input {:type "text"
             :class "form-control"
             :id "robot-check"
             :placeholder "42?"
             :value (if robots-field
                      robots-field
                      "")
             :on-change #(re-frame/dispatch [::events/set-robot-field-value (-> %
                                                                                .-target
                                                                                .-value)])}]]

   [:a {:href "#/contact/create"
             :class "btn btn-default"}
         "Submit"]])

;; contact
(defn contact-panel []
  (let [email-field (re-frame/subscribe [::subs/email-field])
        message-field (re-frame/subscribe [::subs/message-field])
        robots-field (re-frame/subscribe [::subs/robots-field])]
    [:div 
     (nav)
     ;;(hero-container {:title "Contact us"})
     (contact-form @email-field
                   @message-field
                   @robots-field)
     (footer)]))

;; loading panel
(defn loading-panel []
  [:div
   (nav)
   (hero-container {:title "..."})
   (footer)])

;; main
(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    :validators-panel [validators-panel]
    :blog-panel [blog-panel]
    :contact-panel [contact-panel]
    :loading-panel [loading-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [loading? (re-frame/subscribe [::subs/loading?])
        active-panel (re-frame/subscribe [::subs/active-panel])]
    (if @loading?
      [show-panel :loading-panel]
      [show-panel @active-panel])))
