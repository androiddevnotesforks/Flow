plugins {
    id("flow.android.library")
    id("flow.android.library.compose")
    id("kotlin-parcelize")
}

android {
    namespace = "flow.ui"
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:models"))

    implementation(libs.accompanist.flowlayout)
    implementation(libs.androidx.lifecycle.viewModel.savedState)
    implementation(libs.bundles.coil)
}
