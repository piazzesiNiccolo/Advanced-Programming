import Data.Char (toUpper)
-- EXERCISE  1 --

myReplicate  0 _ = []
myReplicate n v = v: myReplicate (n-1) v

myReplicate1 n v = let myReplicate' 0 xs = xs
                       myReplicate' n xs = myReplicate' (n-1) (v:xs)
                   in  myReplicate' n [] 
myReplicate2 n v = map (\_ -> v) [1..n]

myRep n v = take n (map (\_ -> v) [1..])

myRep1 n v = take n [v,v..]

(|>) a f = f a

myRepF  n v = repeat v |> take n

myRep4 n v = myReplicate' n []
            where 
              myReplicate' 0 xs = xs
              myReplicate' n xs = myReplicate' ( n-1) (v:xs)


-- EXERCISE 2 --

sumOddR [] = 0
sumOddR (x:xs) = if odd(x) then x + sumOddR xs else sumOddR xs

tailSumOddR xs = let helpSum acc [] = acc
                     helpSum acc (x:xs) = if odd(x) then helpSum (acc+x) (xs) else helpSum (acc) (xs)
                in helpSum 0 xs

sumOddC x  = foldl (+) 0 (filter odd x)

-- EXERCISE 3 --

replR n [] = []
replR n (x:xs) = myReplicate n x ++ replR  n xs

replC n (x:xs) = foldl (++) [] (map (myReplicate n) (x:xs))
 
-- EXERCISE 4 --
isA (x:xs) = x =='A'

totalLengthR [] = 0
totalLengthR (x:xs) = if isA(x) then length x + totalLengthR xs else totalLengthR xs


tailTotalLengthR xs = let  helpLength acc [] = acc
                           helpLength acc (x:xs) = if isA(x) then helpLength (acc + length(x)) (xs) else helpLength (acc) (xs)
                      in helpLength 0 xs


totalLengthC (x:xs) = foldl (+) 0 (map length (filter isA (x:xs)))

--EXERCISE 5--
filterOddR xs = let helpFilter [] i = []
                    helpFilter (x:xs) i = if odd(i) then x:helpFilter (xs) (i+1) else helpFilter (xs) (i+1)
                in helpFilter xs 0


filterOddC xs = map fst (filter (odd.snd) (zip xs [0..length xs -1]))
            
-- EXCERCISE 6 --

capitalize [] = []
capitalize (x:xs) = toUpper x:xs

titlecaseRHelp [] = []
titlecaseRHelp (x:xs) = capitalize x:titlecaseRHelp xs
titlecaseR s = unwords (titlecaseRHelp (words s))

titlecaseC s = unwords (map capitalize (words s))

-- EXCERCISE 7 --
isPalindrome s = s == reverse s

countVowelsR [] = 0
countVowelsR (x:xs) = if x `elem` "aeiou" then 1+ countVowelsR xs else countVowelsR xs

countVowelPaliR [] = 0
countVowelPaliR (x:xs) = if isPalindrome(x) then countVowelsR x + countVowelPaliR xs else countVowelPaliR xs

countVowelPaliC (x:xs) = foldl (+) 0 (map countVowelsR (filter isPalindrome (x:xs)))

-- EXCERCISE 8 --

myMap f xs = foldl (\acc x -> acc ++ [f x])  [] (xs)

-- EXCERCISE 9 --
data IntTree = Leaf Int | Node (Int, IntTree, IntTree) deriving (Show)

tmap f (Leaf t) = Leaf(f t)
tmap f (Node (v,l,r)) = Node(f v, tmap f l, tmap f r)

succTree = tmap (\x -> x+1) 

sumTree (Leaf l) = l
sumTree (Node(v,l,r)) = v + sumTree l + sumTree r

sumSuccTree t = sumTree (succTree t)
  
