library(C50)
library(foreign)
library(caret)

#data-set
dataset = read.dta("Dwelling.dta")
#remove white space
apply(dataset,2,function(x)gsub('\\s+', '',x))

## set the seed to make your partition reproducible
set.seed(80)


# shuffle the data-frame by rows
shuffledDataSet = dataset[order(runif(nrow(dataset))), ]
## 80% of the sample size
trainingDataSet = shuffledDataSet[1:floor(nrow(shuffledDataSet)*0.80), ]
testingDataSet = shuffledDataSet[(floor(nrow(shuffledDataSet)*0.80)+1): nrow(shuffledDataSet) , ]

View(trainingDataSet)
View(testingDataSet)

#select input features
colums = c("h1","h2","h5","h6","h8a", "h8b","h9a","h9b","h9c","h10","h11","h12", "h13_1","h13_2","h13_3","h13_4","h13_5",
           "h14_1","h14_2","h14_3","h14_4","h14_5","h18_1","h18_2","h18_3","h18_4","h18_5","h18_6","h18_7","h18_8",
           "h21_1","h21_5","h21_6","h21_9","h21_16","h21_20")
x_trainingDataSet = trainingDataSet[ ,colums]
y_trainingDataSet = trainingDataSet$h3
x_testingDataSet  = testingDataSet[ ,colums]
y_testingDataSet = testingDataSet$h3
help("C5.0Control")
#Build Decision tree Model
#winnow: remove unnecessary features
#fuzzyThreshold: disable small effect from data to the model
# CF: effect of the pruning,,,,less than 0.25-->less pruning-->more error
#minCasse: min case child for each node
#trials: an integer specifying the number of boosting iterations. A value of one indicates that a single model is used.
ctrl <- C5.0Control(winnow = TRUE, fuzzyThreshold = TRUE, CF=0.15, label = "Ameer Model For Project", minCases = 3)
model <- C5.0(x_trainingDataSet , as.factor(y_trainingDataSet) , trials = 10, control = ctrl)
summary(model)
predictions <- predict(model, x_testingDataSet)

#calculate accuracy
temp = predictions == y_testingDataSet
accuracy = length(which(temp)) / length(temp) * 100.0
sprintf("The accuracy= %.2f",accuracy)



