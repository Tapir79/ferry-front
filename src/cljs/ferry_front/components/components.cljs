(ns ferry-front.components.components)


(defn lister [items]
  [:ul
   (for [item items]
     ^{:key item} [:li "Item " item])])

