# ComposeNotesApp Room :book: 

<a href="https://www.linkedin.com/in/adel-ayman-2497ab1b3/">
    <img src="https://img.shields.io/badge/LinkedIn-blue?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn Badge"/>
  </a>
  
 **Notes App** That's Built With Kotlin to keep and organize user's notes. This repository contains a Notes App This is an educational App.Use and run this App to learn more about Apps design and best practices you must follow.That's built with Kotlin language with compose, That's implements Coroutines,Room,clean architecture,hilt,StateFlow,Navigation component,etc...

 ![](https://user-images.githubusercontent.com/85571327/206874180-5b9fb911-c883-49f8-add2-82e8cfad6b37.png)
 
 ## Features
- Search
- Add Notes
- Get Notes with realtime changes
- Edit Notes
- Change Note Color
- Add Link
- Add Image
- Delete Note
- Validate WebLink
- Validate Email And Password
- Validate Empty Fields

 ## App Overview

- this screen view all user notes

<img src="https://user-images.githubusercontent.com/85571327/206873030-6a027f72-55d5-44a8-9421-eb97048598b2.jpg" width="300" />


- this screen view note details and edit note

<img src="https://user-images.githubusercontent.com/85571327/206873063-9960f60d-94cc-48a9-82cb-1949504c4b1e.jpg" width="300" />


**Bottom Sheet Content**

<img src="https://user-images.githubusercontent.com/85571327/206873081-448ed6f2-82ce-4f9c-b715-98dbbb1a1f08.jpg" width="300" />



**Add Link**

<img src="https://user-images.githubusercontent.com/85571327/206873092-b385ca08-3bb7-49df-893a-6678a96a01c7.jpg" width="300" />



**Fields Validation**

<img src="https://user-images.githubusercontent.com/85571327/206873126-2fb55899-47ac-4f26-a7eb-a49b1ee32083.jpg" width="300" />

<img src="https://user-images.githubusercontent.com/85571327/206873147-1b697516-2cde-44cf-b2e9-78b1a0c1ff57.jpg" width="300" />

<img src="https://user-images.githubusercontent.com/85571327/206873170-49e9a939-b4b2-465a-b696-e63bede3eb58.jpg" width="300" />



**Add Image**

<img src="https://user-images.githubusercontent.com/85571327/206873187-ed0bc810-2f90-4bbb-bb34-97700cbae45f.jpg" width="300" />


## Built With 🛠

*  [Kotlin](https://kotlinlang.org/) 
*  [Jetpack Compose](https://developer.android.com/jetpack/compose) 
*  [Coroutines](https://developer.android.com/kotlin/coroutines)
*  [StateFlow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/) 
*  MVVM architecture
*  Clean architecture
*  [Navigation component](https://developer.android.com/guide/navigation)
*  [Hilt](https://developer.android.com/training/dependency-injection/hilt-jetpack) 
*  [Room](https://developer.android.com/training/data-storage/room)
*  [Coil](https://coil-kt.github.io/coil/compose/)
*  [Accompanist Permissions](https://github.com/google/accompanist/)
*  [LazyVerticalStaggeredGrid](https://developer.android.com/reference/kotlin/androidx/compose/foundation/lazy/staggeredgrid/package-summary/)
*  Single activity concept 
*  Repository pattern

 ## Project Structure
```
app
 ┗ noteappcompose
 ┃ ┣ data
 ┃ ┃ ┣ di
 ┃ ┃ ┃ ┗ modules
 ┃ ┃ ┃ ┃ ┣ CoroutinesModule.kt
 ┃ ┃ ┃ ┃ ┣ DatabaseModule.kt
 ┃ ┃ ┃ ┃ ┗ RepositoryModule.kt
 ┃ ┃ ┣ repositories
 ┃ ┃ ┃ ┗ NoteRepositoryImpl.kt
 ┃ ┃ ┗ source
 ┃ ┃ ┃ ┗ local
 ┃ ┃ ┃ ┃ ┣ dao
 ┃ ┃ ┃ ┃ ┃ ┗ NoteDao.kt
 ┃ ┃ ┃ ┃ ┣ entities
 ┃ ┃ ┃ ┃ ┃ ┗ NoteEntity.kt
 ┃ ┃ ┃ ┃ ┗ NoteDatabase.kt
 ┃ ┣ domain
 ┃ ┃ ┣ models
 ┃ ┃ ┃ ┣ NoteModel.kt
 ┃ ┃ ┃ ┗ ValidateResult.kt
 ┃ ┃ ┣ repositories
 ┃ ┃ ┃ ┗ NoteRepository.kt
 ┃ ┃ ┣ usecases
 ┃ ┃ ┃ ┣ AddNoteUseCase.kt
 ┃ ┃ ┃ ┣ DeleteNoteUseCase.kt
 ┃ ┃ ┃ ┣ GetNoteDetailsUseCase.kt
 ┃ ┃ ┃ ┣ GetNotesUseCase.kt
 ┃ ┃ ┃ ┣ SearchNotesUseCase.kt
 ┃ ┃ ┃ ┣ ValidateNoteDescriptionUseCase.kt
 ┃ ┃ ┃ ┣ ValidateNoteSubtitleUseCase.kt
 ┃ ┃ ┃ ┣ ValidateNoteTitleUseCase.kt
 ┃ ┃ ┃ ┗ ValidateWebLinkUseCase.kt
 ┃ ┃ ┗ utilitites
 ┃ ┃ ┃ ┗ Exceptions.kt
 ┃ ┣ presentation
 ┃ ┃ ┣ homeScreen
 ┃ ┃ ┃ ┣ components
 ┃ ┃ ┃ ┃ ┣ DefaultCard.kt
 ┃ ┃ ┃ ┃ ┣ NoteItem.kt
 ┃ ┃ ┃ ┃ ┗ SearchField.kt
 ┃ ┃ ┃ ┣ uiStates
 ┃ ┃ ┃ ┃ ┣ HomeUiEvent.kt
 ┃ ┃ ┃ ┃ ┣ NoteItemUiState.kt
 ┃ ┃ ┃ ┃ ┗ NotesUiState.kt
 ┃ ┃ ┃ ┣ HomeScreen.kt
 ┃ ┃ ┃ ┗ HomeViewModel.kt
 ┃ ┃ ┣ noteDetailsScreen
 ┃ ┃ ┃ ┣ components
 ┃ ┃ ┃ ┃ ┣ BottomSheetItem.kt
 ┃ ┃ ┃ ┃ ┣ ColorBox.kt
 ┃ ┃ ┃ ┃ ┣ NoteInputField.kt
 ┃ ┃ ┃ ┃ ┣ ToolBarIconButton.kt
 ┃ ┃ ┃ ┃ ┗ UrlDialog.kt
 ┃ ┃ ┃ ┣ uiStates
 ┃ ┃ ┃ ┃ ┣ InputFieldUiState.kt
 ┃ ┃ ┃ ┃ ┣ LinkUiState.kt
 ┃ ┃ ┃ ┃ ┣ NoteDetailsEvent.kt
 ┃ ┃ ┃ ┃ ┣ NoteDetailsUiState.kt
 ┃ ┃ ┃ ┃ ┗ UiEvent.kt
 ┃ ┃ ┃ ┣ NoteDetailScreen.kt
 ┃ ┃ ┃ ┗ NoteDetailsViewModel.kt
 ┃ ┃ ┗ utility
 ┃ ┃ ┃ ┣ Constants.kt
 ┃ ┃ ┃ ┗ Screen.kt
 ┃ ┣ ui
 ┃ ┃ ┗ theme
 ┃ ┃ ┃ ┣ Color.kt
 ┃ ┃ ┃ ┣ Theme.kt
 ┃ ┃ ┃ ┗ Type.kt
 ┃ ┣ MainActivity.kt
 ┃ ┗ NoteApp.kt
```

## LICENSE
```MIT License

Copyright (c) 2022 adelayman1

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.```
