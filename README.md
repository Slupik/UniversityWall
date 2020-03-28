# UniversityWall

## About

Application created to pass course "Group project" at the university.
Now it's pet project for testing new ideas or to master old ones.

Main reason to create this app is horrible communication in polish universities so I tried to solve the problem.
Implemented features gives to user ability to quickly join to groups and get essential info.

## Comments (problems, requirements, general info)

0. Really short deadline - only few days.
1. Cooperation with other members of team (everybody had own task).
2. Full specification before implementation.
3. Lack of UI designer.

## Architecture and used patterns

It's a multi-module project using MVVM pattern and Clean Architecture.

### Assumptions (MVVM)

0. **View** - fragment or activity and XML file. It represents the UI of the application devoid of any Application Logic. It observes the ViewModel and informs it about the user’s actions.
1. **ViewModel** - exposes streams of data relevant to the View. It’s responsible for transforming the data from the Model. It’ll ask for the data from the Model. Logic of the View.
2. **Model** - represents the data and business logic of the app.

Sources:
 - https://medium.com/upday-devs/android-architecture-patterns-part-3-model-view-viewmodel-e7eeee76b73b
 - https://proandroiddev.com/mvvm-architecture-viewmodel-and-livedata-part-1-604f50cda1
 - https://www.journaldev.com/20292/android-mvvm-design-pattern

## Next Goals

0. Improve quality of a code in UI layer.
1. Improve the look and feel.