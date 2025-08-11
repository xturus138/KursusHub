# KursusHub

KursusHub adalah aplikasi Android yang membantu pengguna menemukan dan mendaftar ke berbagai sekolah atau kursus di seluruh Indonesia. Aplikasi ini menyediakan platform untuk menjelajahi, membandingkan, dan melamar ke institusi pendidikan dengan mudah.

## 🌟 Fitur Utama

* **Autentikasi Pengguna**: Sistem masuk dan daftar menggunakan email dan kata sandi, serta opsi praktis melalui akun Google.
* **Jelajahi Sekolah**: Menampilkan daftar sekolah dari berbagai jenjang (SD, SMP, SMA, SMK) yang diambil dari API publik.
* **Pencarian Cerdas**: Fungsi pencarian untuk menemukan sekolah secara spesifik berdasarkan nama.
* **Filterisasi**: Opsi filter berdasarkan jenjang pendidikan untuk memudahkan pencarian.
* **Detail Sekolah**: Menampilkan informasi rinci setiap sekolah dalam sebuah dialog.
* **Sistem Pendaftaran**: Pengguna dapat "melamar" ke sekolah pilihan yang datanya akan disimpan di Firestore.
* **Status Lamaran**: Halaman khusus untuk melihat riwayat sekolah yang telah dilamar oleh pengguna.
* **Manajemen Sesi**: Pengguna akan tetap masuk ke dalam akunnya hingga melakukan *logout*.
* **Profil Pengguna**: Halaman untuk melihat profil dan melakukan *logout*.

## 🛠️ Teknologi yang Digunakan

* **Bahasa**: [Kotlin](https://kotlinlang.org/)
* **Arsitektur**: MVVM (Model-View-ViewModel)
* **UI**:
    * Android UI (XML Layouts)
    * Material Components
    * View Binding
* **Asynchronous**:
    * Kotlin Coroutines
    * LiveData
* **Networking**:
    * [Retrofit](https://square.github.io/retrofit/) untuk koneksi ke REST API
    * [OkHttp Logging Interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor) untuk *debugging*
* **Manajemen Dependensi**: [Gradle](https://gradle.org/)
* **Firebase**:
    * Firebase Authentication (Email/Password & Google Sign-In)
    * Cloud Firestore sebagai database
* **Penyimpanan Lokal**:
    * DataStore Preferences untuk menyimpan sesi pengguna
* **Navigasi**:
    * Android Jetpack Navigation Component
    * Fragment Manager

## ⚙️ Cara Menjalankan Proyek

1.  **Clone Repositori**
    ```bash
    git clone [https://github.com/xturus138/kursushub.git](https://github.com/xturus138/kursushub.git)
    ```

2.  **Buka di Android Studio**
    * Buka Android Studio.
    * Pilih `Open an existing Android Studio project`.
    * Arahkan ke direktori tempat Anda melakukan *clone* proyek, lalu klik OK.

3.  **Konfigurasi Firebase**
    * Proyek ini menggunakan Firebase untuk autentikasi dan database.
    * Pastikan Anda sudah menambahkan file `google-services.json` Anda sendiri ke dalam direktori `app/`. Anda bisa mendapatkannya dari Firebase Console setelah membuat proyek baru.

4.  **Jalankan Aplikasi**
    * Tunggu hingga Gradle selesai melakukan *sync*.
    * Pilih emulator atau perangkat fisik.
    * Klik tombol **Run** (▶️) di Android Studio.

## 📂 Struktur Proyek

Proyek ini mengikuti arsitektur MVVM dengan struktur direktori sebagai berikut:
com.example.kursushub
│
├── data
│   ├── local       # Penyimpanan lokal (DataStore)
│   ├── model       # Data class untuk API & aplikasi
│   ├── remote      # Definisi Retrofit API (ApiService)
│   └── repository  # Mengelola sumber data (API & Firebase)
│
├── ui
│   ├── adapter     # RecyclerView Adapters
│   ├── auth        # Aktivitas & ViewModel untuk login/register
│   ├── onBoarding  # Fragment untuk halaman perkenalan
│   └── main        # Aktivitas & Fragment utama setelah login
│
└── di              # (Jika ada) Dependecy Injection
