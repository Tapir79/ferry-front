(ns ferry-front.views.booking-form
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent :refer [atom]]
            [ferry-front.events :as events]
            [ferry-front.subs :as subs]
            [stylefy.core :refer [use-style]]))

(def booking-form-style {:width "97.5%"
                         :max-width "1590px"})

(defn styled-select [id label placeholder options]
  [:div {:class "flex-grow flex-shrink mb-1 sm:m-1 w-full sm:w-auto"}
   [:label {:for id :class "hidden sm:block text-sm font-bold sm:pr-1"} label]
   [:select {:id id :class "rounded py-2 ph-3 bg-white w-full"}
    [:option {:class "text-grey-darker"} placeholder ]
    (for [option options]
      ^{:key option} [:option option])
    ]])

(defn booking-form []
  [:div (use-style booking-form-style {:class "sm:w-full -ml-1 sm:mx-1 pb-1"})
    [:form {:class "w-full flex flex-wrap items-center bg-blue-lighter rounded p-1 pb-0 sm:p-2 ml-2 mb-1"}
      (styled-select "route-selection" "Route" "Select route" (list "eka" "toka"))
      (styled-select "from" "from" "Departure harbor" (list "satama1" "satama2"))
      (styled-select "to" "to" "Arrival harbor" (list "satama1" "satama2"))]])
