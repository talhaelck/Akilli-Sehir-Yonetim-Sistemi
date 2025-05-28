# Şehir Yönetim Sistemi

Bu proje, şehir yönetimi için geliştirilmiş bir masaüstü uygulamasıdır. Şehir kaynaklarının ve sistemlerinin yönetimini kolaylaştırmak amacıyla Java Swing kullanılarak geliştirilmiştir.

Uygulama, sistem yöneticilerinin şehir kaynaklarını kontrol etmesine, sistem durumunu takip etmesine ve genel yönetimi sağlamasına olanak tanır. Modern ve kullanıcı dostu arayüzü sayesinde, karmaşık yönetim işlemlerini basitleştirerek etkili bir kontrol sağlar.

## Özellikler

- Modern ve kullanıcı dostu arayüz tasarımı
- Admin girişi ve yönetim paneli
- Gradyan arka planlar ve özel tasarlanmış butonlar
- Güvenli giriş sistemi

## Başlangıç

### Gereksinimler

- Java JDK 8 veya üzeri
- Maven

### Admin Girişi

1. Ana ekrandan "Admin Girişi" butonuna tıklayın
2. Aşağıdaki bilgilerle giriş yapın:
   - Kullanıcı adı: talha
   - Şifre: talha123

### Arayüz Özellikleri

- Modern, futuristik tasarım
- Duyarlı ve kullanıcı dostu form elemanları
- Görsel geri bildirimler
- Kolay gezinme için sezgisel düğmeler

## Proje Yapısı

src/main/java/com/mycompany/sehir/
├── AdminGirisEkrani.java    # Admin giriş arayüzü
├── AdminPaneli.java         # Admin kontrol paneli
├── AnaGirisEkrani.java      # Ana giriş ekranı
├── Admin.java               # Admin sınıfı
├── SistemYoneticisi.java   # Sistem yönetici işlemleri
└── utils/                   # Yardımcı sınıflar
