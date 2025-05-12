package com.example.notesapp.core

import com.example.notesapp.core.data.di.managerModule
import com.example.notesapp.core.data.di.remoteModule
import com.example.notesapp.core.data.di.useCaseModule
import org.koin.core.module.Module

fun getCoreModules(): List<Module> = listOf(managerModule, remoteModule, useCaseModule)