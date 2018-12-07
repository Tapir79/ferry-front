(ns ferry-front.views.navigation
  (:require
    [reagent.core :as reagent]
    [re-frame.core :as re-frame]
    [reitit.frontend :as reitit]
    [reitit.frontend.easy :as rfe]
    [reitit.coercion.schema :as rsc]
    [ferry-front.components.loader :refer [compass-loader]]
    [ferry-front.views.confirm-booking :as confirm-booking]
    [ferry-front.views.analysis :as analysis]
    [ferry-front.views.booking :refer [booking-main]]
    [ferry-front.leaflet.core :refer [leaflet]]
    [ferry-front.views.map :refer [main-map]]))

(defonce match (reagent/atom nil))

(def link-style "no-underline cursor-pointer hover:text-white text-grey-light")

  (defn main-navigation []
    [:nav
     [:ul {:class "list-reset p-1 sm:ml-2 lg:ml-4 flex font-medium flex flex-wrap"}
      [:li {:class "mr-2 sm:mr-6"}
       [:a {:href (rfe/href ::booking)
            :class (str link-style " text-white")} "Booking"]]
      [:li {:class "mr-2 sm:mr-6"}
       [:a {:href (rfe/href ::route-test)
            :class link-style} "Timetables"]]
      [:li {:class "mr-2 sm:mr-6"}
       [:a {:href (rfe/href ::analysis)
            :class link-style} "Analysis"]]]
     (if @match
       (let [view (:view (:data @match))]
         [view @match]))])

(defn main-loader []
  [:div {:class "flex justify-center items-center h-screen w-screen"}
   (compass-loader)])

(defn booking-panel    ;; this is new
  []
  (let [ready?  (re-frame/subscribe [:initialised?])]
    (if-not @ready?         ;; do we have good data?
      (main-loader)   ;; tell them we are working on it
      [:div {:class "flex"}
         [booking-main]
          [:div {:class "hidden sm:block"}
            [main-map]]])))

(def routes
  (reitit/router
    ["/"
     [""
      {:name ::booking
       :view booking-panel}]
     ["analysis"
      {:name ::analysis
       :view analysis/chart-component}]
     ["confirm-booking"
      {:name :confirm-booking
       :view confirm-booking/main-view}]]
    {:data {:coercion rsc/coercion}}))

(defn init-routes! []
  (rfe/start! routes
              (fn [m] (reset! match m))
              {:use-fragment false
               :path-prefix "/"}))
