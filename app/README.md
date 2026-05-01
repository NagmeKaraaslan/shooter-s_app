# Shooter's
creater: Nağme Karaaaslan
**Mobil Programlama Dersi — Final Projesi**
Veritabanı: Firebase Realtime Database + Firebase Storage  
Kimlik Doğrulama: Firebase Authentication

---

## Proje Hakkında

Shooter's, moda ve fotoğrafçılık dünyasına özel bir portfolyo ve keşif uygulamasıdır. Modeller, fotoğrafçılar ve ajanslar bu uygulama aracılığıyla kendi profillerini oluşturabilir, çekim fotoğraflarını paylaşabilir ve birbirlerinin çalışmalarını keşfedebilir.

---

## Kullanıcı Tipleri

### Model
- Fotoğraf yükleyebilir (Shoots sekmesi)
- Beğendiği / kaydetmek istediği çekimleri kaydedebilir (Shoot Goals sekmesi)
- Profil bilgilerini düzenleyebilir (Bilgiler sekmesi): ad, boy, ölçüler, bio

### Fotoğrafçı
- Profil sayfasında iletişim bilgilerini paylaşır (telefon, e-posta, Instagram)
- Onaylı rozeti görünür (bkz. Gelecek Özellikler)

### Ajans
- Profil sayfasında iletişim bilgilerini paylaşır
- Onaylı rozeti görünür (bkz. Gelecek Özellikler)

---

## Teknik Özellikler

### Ekranlar (Activity)
1. **Giriş Ekranı** — Firebase Authentication ile e-posta / şifre girişi
2. **Kayıt Ekranı** — Kullanıcı tipi seçimi (model / fotoğrafçı / ajans), e-posta, şifre
3. **Ana Sayfa** — Tüm kullanıcıların yüklediği fotoğraflar, keşfet akışı
4. **Profil Sayfası** — Kullanıcı tipine göre Shoots / Shoot Goals / Bilgiler sekmeleri
5. **Fotoğraf Detay Ekranı** — Büyük görünüm, beğeni, kaydetme

### Kullanılan Teknolojiler
- **Firebase Authentication** — kullanıcı kaydı ve girişi
- **Firebase Realtime Database** — kullanıcı verileri, fotoğraf metadata, beğeniler, kaydedilenler
- **Firebase Storage** — fotoğraf dosyaları
- **Glide** — fotoğraf yükleme ve önbellekleme
- **RecyclerView** — grid fotoğraf listesi
- **Intent** — ekranlar arası geçiş ve veri aktarımı

### Firebase Veri Modeli
```
users/
  {uid}/
    ad: String
    tip: "model" | "fotografci" | "ajanss"
    bio: String
    iletisim: String        (fotoğrafçı ve ajans için)
    boy: String             (model için)
    olculer: String         (model için)

shoots/
  {shootId}/
    yukleyenUid: String
    fotografUrl: String
    aciklama: String
    tarih: Long
    beğeniSayisi: Int
    begeniler/
      {uid}: true

shootGoals/
  {uid}/
    {shootId}: true
```

---

## Gelecekte Eklenecek Özellikler

> Aşağıdaki özellikler mevcut versiyonda kodlanmamıştır. Proje büyüdükçe eklenmesi planlanmaktadır.

- **Onay Sistemi:** Fotoğrafçı ve ajans hesapları admin tarafından doğrulanacak, onaylı hesaplara özel rozet verilecektir. Bu işlem backend tarafında bir admin paneli gerektirmektedir.
- **Mesajlaşma:** Kullanıcılar arasında doğrudan mesaj
- **Arama:** Kullanıcı adı veya kategoriye göre arama
- **Bildirimler:** Beğeni ve kaydetme bildirimleri

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
| EditText kullanımı | ✅ | Kayıt, giriş, fotoğraf açıklaması, bio |
| setOnClickListener | ✅ | Tüm butonlar |
| if / else kontrolleri | ✅ | Giriş doğrulama, kullanıcı tipi kontrolü |
| Firebase veritabanı | ✅ | Realtime Database + Storage |
| Veri kaydetme | ✅ | Fotoğraf, kullanıcı bilgisi |
| Veri listeleme | ✅ | Ana sayfa akışı, profil sekmeleri |
| Anlamlı senaryo | ✅ | Moda/fotoğrafçılık portfolyo uygulaması |
