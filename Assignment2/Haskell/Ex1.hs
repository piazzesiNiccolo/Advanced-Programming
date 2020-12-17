module Ex1(
ListBag(LB)
,wf
,empty
,singleton
,fromList
,isEmpty
,mul
,toList
,sumBag
) where

data ListBag a = LB [(a, Int)]
    deriving (Show, Eq)

findElem :: Eq a => a -> ListBag a -> Bool 
findElem a (LB []) = False
findElem a (LB ((x,y):xs)) = x == a || findElem a (LB xs)

wf :: Eq a => ListBag a -> Bool
wf (LB []) = True
wf (LB ((x,y):xs)) = not(findElem x (LB xs)) &&  wf (LB xs)

empty :: ListBag a
empty = LB []

singleton :: a -> ListBag a
singleton v = LB[(v,1)]

howMany :: Eq a => a -> [a] -> Int
howMany e [] = 0
howMany e (x:xs) = let countHelp y acc [] = acc
                       countHelp y acc (x:xs) = 
                        if y==x then countHelp y (acc+1) xs
                        else countHelp y acc xs
                    in countHelp e 0 (x:xs)

makeTupleList :: Eq a => [a] -> [(a, Int)]
makeTupleList [] = []
makeTupleList (x:xs) = (x, howMany x (x:xs)):makeTupleList (filter (/= x) xs)

fromList :: Eq a => [a] -> ListBag a
fromList [] = LB []
fromList (x:xs) = LB (makeTupleList (x:xs))

isEmpty :: ListBag a -> Bool
isEmpty (LB []) = True 
isEmpty (LB x) = False


mul :: Eq a => a -> ListBag a -> Int
mul e (LB []) = 0
mul e (LB ((x,y):xs)) = if e==x then y else mul e (LB xs)

toList (LB []) = []
toList (LB ((x,y):xs)) = replicate y x ++ toList (LB xs)

sumBag :: Eq a => ListBag a -> ListBag a -> ListBag a
sumBag (LB bag1) (LB bag2) = fromList (toList (LB bag1) ++ toList (LB bag2))

