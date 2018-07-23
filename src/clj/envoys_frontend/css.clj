(ns envoys-frontend.css
  (:require [garden.def :refer [defstyles]]))

(defstyles screen
  [:body {:color "#fdfdfd"
          :background-color "#252525"}

   [:nav
    [:a
     [:&:hover
      {"text-decoration" "none"}]]]

   [:.btn-default:hover
    {"background-position" "0 -1px"}]])

