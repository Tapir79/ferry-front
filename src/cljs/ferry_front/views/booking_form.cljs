(ns ferry-front.views.booking-form
  (:require [re-frame.core :as re-frame]
            [ferry-front.subs :as subs]
            [stylefy.core :as stylefy]))

(enable-console-print!)

(defn handle-search-button-click []
  ())

(def booking-form-style {:width "97.5%"})
(def icon-style {:top "29px" :left "0.5em" :pointer-events "none"
                 ::stylefy/media {{:max-width "576px"} {:top "9px"}}})

(defn styled-select [id label placeholder options on-change-function icon]
  [:div {:class "flex-grow flex-shrink mb-1 sm:m-1 w-full sm:w-auto relative"}
   [:label {:for id :class "hidden sm:block text-sm font-bold sm:pr-1 text-grey-darker"} label]
   [:select {:id id :class "rounded py-2 pl-8 px-1 bg-white w-full" :on-change on-change-function }
    [:option {:class "text-grey-darker"} placeholder]
    (options)]
   [:i (stylefy/use-style icon-style {:class (str icon " " "absolute z-10 text-grey-darker")})]])

(defn search-button []
  [:div {:class "flex-grow flex-shrink w-full sm:w-auto my-1 sm:m-1"}
   [:button {:class "bg-blue hover:bg-blue-dark font-semibold text-white p-2 rounded w-full sm:w-4/5"
             :type "button"
             :on-click handle-search-button-click
             } "Search"]])

(defn get-lines-options [lines]
  (fn []
    (for [line @lines]
      [:option {:key (:id line) :value (:id line)} (:name_fi line) ])))

(defn get-stops-options [stops]
  (fn []
    (for [stop @stops]
      [:option {:key (:id stop) :value (:id stop)} (:name stop) ])))

(defn form [lines stops]
  [:div (stylefy/use-style booking-form-style {:class "sm:w-full -ml-1 lg:ml-3 sm:mx-1 pb-1"})
   [:form {:class "w-full flex flex-wrap items-center sm:items-end bg-blue-lighter rounded p-1 pb-0 sm:p-2 ml-2 mb-1"}
    (styled-select "route-selection" "Route" "Select route" (get-lines-options lines) #(println "eka") "fas fa-route")
    (styled-select "from" "from" "Departure harbor" (get-stops-options stops) #(println "satama") "fas fa-anchor")
    (styled-select "to" "to" "Arrival harbor" (get-stops-options stops) #(println "arrival") "fas fa-anchor")
    (search-button)]])

(defn booking-form []
  (let [lines (re-frame/subscribe [::subs/lines])
        stops (re-frame/subscribe [::subs/stops])]
  (when-not (or (empty? @lines) (empty? @stops)))
    (form lines stops)))
