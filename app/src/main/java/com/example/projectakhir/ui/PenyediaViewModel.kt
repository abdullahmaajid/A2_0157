package com.example.projectakhir.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projectakhir.data.applications.PerpustakaanApplications
import com.example.projectakhir.ui.buku.viewmodel.*
import com.example.projectakhir.ui.kategori.viewmodel.*
import com.example.projectakhir.ui.kategori.viewmodel.*
import com.example.projectakhir.ui.penerbit.viewmodel.*
import com.example.projectakhir.ui.penulis.viewmodel.HomeViewModelPenulis

object PenyediaViewModel {
    val Factory = viewModelFactory {
        // Buku
        initializer {
            HomeViewModelBuku(
                PerpustakaanApplication().container.bukuRepository
            )
        }
        initializer {
            InsertViewModelBuku(
                PerpustakaanApplication().container.bukuRepository
            )
        }
        initializer {
            DetailViewModelBuku(
                createSavedStateHandle(),
                PerpustakaanApplication().container.bukuRepository
            )
        }
        //initializer {
            //UpdateViewModelBuku(
                //createSavedStateHandle(),
                //PerpustakaanApplication().container.bukuRepository
           // )
       // }
        // initializer {
           // DeleteViewModelBuku(
              //  createSavedStateHandle(),
               // PerpustakaanApplication().container.bukuRepository
           // )
       // }

        // Kategori
        initializer {
            HomeViewModelKategori(
                //createSavedStateHandle(),
                PerpustakaanApplication().container.kategoriRepository)
        }
        initializer {
           InsertViewModelKategori(
               createSavedStateHandle(),
              PerpustakaanApplication().container.kategoriRepository
          )
        }
       initializer {
          DetailViewModelKategori(
              createSavedStateHandle(),
               PerpustakaanApplication().container.kategoriRepository
           )
        }
        initializer {
             UpdateViewModelKategori(
                 createSavedStateHandle(),
                 PerpustakaanApplication().container.kategoriRepository
             )
         }
        // initializer {
            // DeleteViewModelKategori(
                // createSavedStateHandle(),
                // PerpustakaanApplication().container.kategoriRepository
            // )
        // }

        // Penulis
        initializer {
            HomeViewModelPenulis(
                 PerpustakaanApplication().container.penulisRepository
             )
         }
        // initializer {
           //  InsertViewModelPenulis(
              //  PerpustakaanApplication().container.penulisRepository
           // )
       // }
       // initializer {
          // DetailViewModelPenulis(
               //  createSavedStateHandle(),
                // PerpustakaanApplication().container.penulisRepository
           //  )
        // }
        // initializer {
           //  UpdateViewModelPenulis(
//                createSavedStateHandle(),
//                PerpustakaanApplication().container.penulisRepository
//            )
//        }
//        initializer {
//            DeleteViewModelPenulis(
//                createSavedStateHandle(),
//                PerpustakaanApplication().container.penulisRepository
//            )
//        }
//
        //Penerbit
       initializer {
            HomeViewModelPenerbit(
                PerpustakaanApplication().container.penerbitRepository
            )
        }
//        initializer {
//            InsertViewModelPenerbit(
//                PerpustakaanApplication().container.penerbitRepository
//            )
//        }
//        initializer {
//            DetailViewModelPenerbit(
//                createSavedStateHandle(),
//                PerpustakaanApplication().container.penerbitRepository
//            )
//        }
//        initializer {
//            UpdateViewModelPenerbit(
//                createSavedStateHandle(),
//                PerpustakaanApplication().container.penerbitRepository
//            )
//        }
//        initializer {
//            DeleteViewModelPenerbit(
//                createSavedStateHandle(),
//                PerpustakaanApplication().container.penerbitRepository
//            )
//        }
    }
}

fun CreationExtras.PerpustakaanApplication(): PerpustakaanApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PerpustakaanApplications)