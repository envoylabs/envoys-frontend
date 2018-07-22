(ns envoys-frontend.css
  (:require [garden.def :refer [defstyles]]))

(defstyles screen
  [:body {:color "#fdfdfd"}

   [:nav
    [:a
     [:&:hover
      {"text-decoration" "none"}]]]

   ])
