(ns ferry-front.views.navigation
  (:require
    [reagent.core :as reagent]
    [re-frame.core :as re-frame]
    [reitit.frontend :as reitit]
    [reitit.frontend.easy :as rfe]
    [reitit.coercion :as rc]
    [reitit.coercion.schema :as rsc]
    [ferry-front.views :as views]
    [ferry-front.components.loader :refer [compass-loader]]
    [ferry-front.views.confirm-booking :as confirm-booking]))

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
       [:a {:href (rfe/href ::route-test)
            :class link-style} "Analysis"]]]
     (if @match
       (let [view (:view (:data @match))]
         [view @match]))])

(defn main-loader []
  [:div {:class "flex justify-center items-center h-screen w-screen"}
   (compass-loader)])

(defn top-panel    ;; this is new
  []
  (let [ready?  (re-frame/subscribe [:initialised?])]
    (if-not @ready?         ;; do we have good data?
      (main-loader)   ;; tell them we are working on it
      [views/main-panel])))


(defn route-test []
  [views/test-view])

(def routes
  (reitit/router
    ["/"
     [""
      {:name ::booking
       :view top-panel}]
     ["route-test"
      {:name ::route-test
       :view route-test}]
     ["confirm-booking"
      {:name :confirm-booking
       :view confirm-booking/main-view}]]
    {:data {:coercion rsc/coercion}}))

(defn init-routes! []
  (rfe/start! routes
              (fn [m] (reset! match m))
              {:use-fragment false
               :path-prefix "/"}))
