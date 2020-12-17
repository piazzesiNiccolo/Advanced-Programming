module Ex2 where

import Ex1 

instance Foldable ListBag where
    foldr f acc (LB []) = acc
    foldr f acc (LB ((x,y):xs)) = f x (foldr f acc (LB xs))