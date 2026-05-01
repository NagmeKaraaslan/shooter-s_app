# Shooter's
Creator: Nagme Karaaslan  
**Mobile Programming Course — Final Project**  
Database: Firebase Realtime Database + Firebase Storage  
Authentication: Firebase Authentication

---

## About the Project

Shooter's is a portfolio and discovery application dedicated to the fashion and photography world. Models, photographers, and agencies can create their own profiles, share their shoot photos, and discover each other's work through this application.

---

## User Types

### Model
- Shares contact information on profile page (phone-not required-, email, Instagram, ect.)
- Can upload photos (Shoots tab)
- Can save liked / bookmarked shoots (Shoot Goals tab)
- Can edit profile information (Information tab): name, height, measurements, bio

### Photographer
- Shares contact information on profile page (phone-not required-, email, Instagram, ect.)
- Verified badge is visible (see Future Features)

### Agency
- Shares contact information on profile page(phone-not required-, email, Instagram, ect.)
- Verified badge is visible (see Future Features)

---

## Technical Features

### Screens (Activities)
1. **Login Screen** — Email / password login with Firebase Authentication
2. **Register Screen** — User type selection (model / photographer / agency), email, password
3. **Home Page** — Photos uploaded by all users, discovery feed
4. **Profile Page** — Shoots / Shoot Goals / Information tabs according to user type
5. **Photo Detail Screen** — Large view, like, save

### Technologies Used
- **Firebase Authentication** — user registration and login
- **Firebase Realtime Database** — user data, photo metadata, likes, bookmarks
- **Firebase Storage** — photo files
- **Glide** — photo loading and caching
- **RecyclerView** — grid photo list
- **Intent** — screen transitions and data transfer

### Firebase Data Model
```
users/
  {uid}/
    ad: String
    tip: "model" | "fotografci" | "ajans"
    bio: String
    iletisim: String        (for photographer and agency)
    boy: String             (for model)
    olculer: String         (for model)

shoots/
  {shootId}/
    yukleyenUid: String
    fotografUrl: String
    aciklama: String
    tarih: Long
    begeniSayisi: Int
    begeniler/
      {uid}: true

shootGoals/
  {uid}/
    {shootId}: true
```

---

## Future Features

> The following features are not coded in the current version. They are planned to be added as the project grows.

- **Verification System:** Photographer and agency accounts will be verified by an admin, and verified accounts will receive a special badge. This process requires an admin panel on the backend side.
- **Messaging:** Direct messaging between users
- **Search:** Search by username or category
- **Notifications:** Like and bookmark notifications

---

## Setup

1. Open the project in Android Studio
2. Add the `google-services.json` file from Firebase Console to the `app/` folder
3. Enable the following in Firebase Console:
    - Authentication → Email/Password
    - Realtime Database
    - Storage
4. Run the project (minimum SDK: 24)

---

## Project Requirements Fulfillment Table

| Requirement | Fulfilled? | Where? |
|---|---|---|
| At least 3 Activities | ✅ | Login, Register, Home, Profile, Detail |
| Intent transitions | ✅ | Between all screens |
| EditText usage | ✅ | Register, login, photo description, bio |
| setOnClickListener | ✅ | All buttons |
| if / else controls | ✅ | Login validation, user type check |
| Firebase database | ✅ | Realtime Database + Storage |
| Data saving | ✅ | Photo, user information |
| Data listing | ✅ | Home feed, profile tabs |
| Meaningful scenario | ✅ | Fashion/photography portfolio app |

'''##TÜRKÇE / TURKISH'''

# Shooter's
yaratıcı: Nagme Karaaslan
**Mobil Programlama Dersi — Final Projesi**
Veritabanı: Firebase Realtime Database + Firebase Storage  
Kimlik Dogrulama: Firebase Authentication

---

## Proje Hakkında

Shooter's, moda ve fotografçılık dünyasına özel bir portfolyo ve keşif uygulamasıdır. Modeller, fotografçılar ve ajanslar bu uygulama aracılıgıyla kendi profillerini oluşturabilir, çekim fotograflarını paylaşabilir ve birbirlerinin çalışmalarını keşfedebilir.

---

## Kullanıcı Tipleri

### Model
- Fotograf yükleyebilir (Shoots sekmesi)
- Begendigi / kaydetmek istedigi çekimleri kaydedebilir (Shoot Goals sekmesi)
- Profil bilgilerini düzenleyebilir (Bilgiler sekmesi): ad, boy, ölçüler, bio

### Fotografçı
- Profil sayfasında iletişim bilgilerini paylaşır (telefon, e-posta, Instagram)
- Onaylı rozeti görünür (bkz. Gelecek Özellikler)

### Ajans
- Profil sayfasında iletişim bilgilerini paylaşır
- Onaylı rozeti görünür (bkz. Gelecek Özellikler)

---

## Teknik Özellikler

### Ekranlar (Activity)
1. **Giriş Ekranı** — Firebase Authentication ile e-posta / şifre girişi
2. **Kayıt Ekranı** — Kullanıcı tipi seçimi (model / fotografçı / ajans), e-posta, şifre
3. **Ana Sayfa** — Tüm kullanıcıların yükledigi fotograflar, keşfet akışı
4. **Profil Sayfası** — Kullanıcı tipine göre Shoots / Shoot Goals / Bilgiler sekmeleri
5. **Fotograf Detay Ekranı** — Büyük görünüm, begeni, kaydetme

### Kullanılan Teknolojiler
- **Firebase Authentication** — kullanıcı kaydı ve girişi
- **Firebase Realtime Database** — kullanıcı verileri, fotograf metadata, begeniler, kaydedilenler
- **Firebase Storage** — fotograf dosyaları
- **Glide** — fotograf yükleme ve önbellekleme
- **RecyclerView** — grid fotograf listesi
- **Intent** — ekranlar arası geçiş ve veri aktarımı

### Firebase Veri Modeli
```
users/
  {uid}/
    ad: String
    tip: "model" | "fotografci" | "ajans"
    bio: String
    iletisim: String        (fotografçı ve ajans için)
    boy: String             (model için)
    olculer: String         (model için)

shoots/
  {shootId}/
    yukleyenUid: String
    fotografUrl: String
    aciklama: String
    tarih: Long
    begeniSayisi: Int
    begeniler/
      {uid}: true

shootGoals/
  {uid}/
    {shootId}: true
```

---

## Gelecekte Eklenecek Özellikler

> Aşagıdaki özellikler mevcut versiyonda kodlanmamıştır. Proje büyüdükçe eklenmesi planlanmaktadır.

- **Onay Sistemi:** Fotografçı ve ajans hesapları admin tarafından dogrulanacak, onaylı hesaplara özel rozet verilecektir. Bu işlem backend tarafında bir admin paneli gerektirmektedir.
- **Mesajlaşma:** Kullanıcılar arasında dogrudan mesaj
- **Arama:** Kullanıcı adı veya kategoriye göre arama
- **Bildirimler:** Begeni ve kaydetme bildirimleri

---

## Kurulum

1. Projeyi Android Studio'da aç
2. Firebase Console'dan `google-services.json` dosyasını `app/` klasörüne ekle
3. Firebase Console'da şunları etkinleştir:
    - Authentication → Email/Password
    - Realtime Database
    - Storage
4. Projeyi çalıştır (minimum SDK: 24)

---

## Proje Gereksinimleri Karşılama Tablosu

| Gereksinim | Karşılandı mı? | Nerede? |
|---|---|---|
| En az 3 Activity | ✅ | Giriş, Kayıt, Ana Sayfa, Profil, Detay |
| Intent ile geçiş | ✅ | Tüm ekranlar arası |
| EditText kullanımı | ✅ | Kayıt, giriş, fotograf açıklaması, bio |
| setOnClickListener | ✅ | Tüm butonlar |
| if / else kontrolleri | ✅ | Giriş dogrulama, kullanıcı tipi kontrolü |
| Firebase veritabanı | ✅ | Realtime Database + Storage |
| Veri kaydetme | ✅ | Fotograf, kullanıcı bilgisi |
| Veri listeleme | ✅ | Ana sayfa akışı, profil sekmeleri |
| Anlamlı senaryo | ✅ | Moda/fotografçılık portfolyo uygulaması |
