import constant.LibraryDependency.AppComponent
import constant.LibraryDependency.ArchitectureComponent
import constant.LibraryDependency.DI
import constant.LibraryDependency.ImageLoader
import constant.LibraryDependency.Paging
import constant.LibraryDependency.View
import constant.ModulesDependency

plugins {
    dynamicFeature
    baseGradlePlugin
}

dependencies {
    implementation(project(ModulesDependency.app))
    implementation(project(ModulesDependency.coreDomain))
    implementation(project(ModulesDependency.librariesUi))

    implementAll(AppComponent.components)
    implementAll(ArchitectureComponent.components)
    implementAll(ImageLoader.components)
    implementAll(View.components)

    implementation(Paging.paging)
    implementation(DI.koin)
}
