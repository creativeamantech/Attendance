# 🤖 Android Attendance Management App — Complete Prompt Guide

> **Role:** You are a world-class Senior Android Developer and Mobile UX Architect with 50 years of combined experience building enterprise-grade Android applications for Fortune 500 companies. You have deep expertise in Kotlin, Jetpack Compose, Material Design 3, Google Maps SDK, Geofencing APIs, Google Sheets API, Firebase, and modern Android architecture patterns. You think simultaneously as an engineer, UX designer, and business strategist.
>
> Build a **COMPLETE, PRODUCTION-READY Android application** for Employee Attendance Management with location-based check-in/check-out, custom working hours per employee, and Google Sheets as the backend database. Every screen, component, animation, color, and interaction must be fully specified and implemented.

---

## 📦 TECH STACK — MANDATORY

| Category | Technology |
|---|---|
| Language | Kotlin (100%, no Java) |
| UI Framework | Jetpack Compose + Material Design 3 |
| Architecture | MVVM + Clean Architecture + UDF |
| Navigation | Jetpack Navigation Compose (deep link support) |
| DI | Hilt (Dagger Hilt) |
| Async | Kotlin Coroutines + StateFlow + SharedFlow |
| Networking | Retrofit 2 + OkHttp + Moshi |
| Location | Google Play Services — FusedLocationProviderClient |
| Geofencing | GeofencingClient (com.google.android.gms.location) |
| Maps | Google Maps Compose SDK |
| Local DB | Room Database (offline caching + sync queue) |
| Remote DB | Google Sheets API v4 (Service Account + OAuth 2.0) |
| Auth | Firebase Authentication (Google Sign-In + Email) |
| Push | Firebase Cloud Messaging (FCM) |
| Background | WorkManager (sync, alerts, reminders) |
| Image Loading | Coil |
| Charts | Vico Charts |
| Permissions | Accompanist Permissions |
| Animations | Compose Animation + Lottie for Android |
| Local Storage | DataStore Preferences (encrypted — Security Crypto) |
| Crash Reporting | Firebase Crashlytics |
| Analytics | Firebase Analytics |
| Testing | JUnit 5 + MockK + Turbine + Compose UI Testing |
| Build | Gradle KTS + BuildConfig + ProGuard/R8 |

---

## 🎨 DESIGN SYSTEM — COMPLETE SPECIFICATION

### Color Palette (Material 3 + Custom)

#### Light Theme

| Token | Hex | Usage |
|---|---|---|
| Primary | `#1A73E8` | Buttons, active icons, links |
| OnPrimary | `#FFFFFF` | Text on primary |
| PrimaryContainer | `#D3E3FD` | Selected states, highlights |
| Secondary | `#00BFA5` | Check-in success, active status |
| OnSecondary | `#FFFFFF` | Text on secondary |
| SecondaryContainer | `#B2DFDB` | Secondary surfaces |
| Tertiary | `#F9AB00` | Warnings, late alerts |
| TertiaryContainer | `#FFF3CD` | Warning backgrounds |
| Error | `#D93025` | Absent, out-of-zone, errors |
| ErrorContainer | `#FDECEA` | Error backgrounds |
| Background | `#F8F9FA` | App background |
| Surface | `#FFFFFF` | Card surfaces |
| SurfaceVariant | `#F1F3F4` | Input fields, inactive areas |
| OnSurface | `#202124` | Primary text |
| Outline | `#DADCE0` | Borders, dividers |
| OutlineVariant | `#E8EAED` | Subtle dividers |

#### Dark Theme

| Token | Hex |
|---|---|
| Background | `#0D1117` |
| Surface | `#161B22` |
| SurfaceVariant | `#21262D` |
| OnSurface | `#E6EDF3` |
| Primary | `#82B4FF` |
| Secondary | `#4DD0C4` |

#### Status Colors (Badges & Chips)

| Status | Hex | Usage |
|---|---|---|
| PRESENT | `#34A853` | Green — on time |
| LATE | `#F9AB00` | Amber — arrived late |
| ABSENT | `#D93025` | Red — no check-in |
| HALF_DAY | `#FF7043` | Deep Orange |
| ON_LEAVE | `#7C4DFF` | Purple |
| WORK_FROM_HOME | `#00ACC1` | Cyan |
| HOLIDAY | `#EC407A` | Pink |
| OUT_OF_ZONE | `#FF5722` | Red-Orange |

---

### Typography (Inter Font via Google Fonts)

| Style | Size | Weight | Usage |
|---|---|---|---|
| displayLarge | 57sp | Bold | Hero numbers (hours worked) |
| displayMedium | 45sp | SemiBold | Dashboard stats |
| displaySmall | 36sp | SemiBold | Section heroes |
| headlineLarge | 32sp | Bold | Screen titles |
| headlineMedium | 28sp | SemiBold | Card titles |
| headlineSmall | 24sp | Medium | Sub-section headers |
| titleLarge | 22sp | SemiBold | List item headers |
| titleMedium | 16sp | SemiBold | Labels, tab titles |
| titleSmall | 14sp | Medium | Secondary labels |
| bodyLarge | 16sp | Regular | Primary body text |
| bodyMedium | 14sp | Regular | Secondary body text |
| bodySmall | 12sp | Regular | Captions, hints |
| labelLarge | 14sp | SemiBold | Buttons |
| labelMedium | 12sp | Medium | Chips, badges |
| labelSmall | 10sp | Medium | Micro labels, timestamps |

---

### Shape System

| Name | Radius | Used For |
|---|---|---|
| ExtraSmall | 4dp | Chips, badges |
| Small | 8dp | Input fields, small cards |
| Medium | 12dp | Standard cards |
| Large | 16dp | Bottom sheets, dialogs |
| ExtraLarge | 28dp | FAB, hero cards |
| Full | CircleShape | Avatars, status dots |

---

### Spacing System

```
2dp  — Micro gap
4dp  — XSmall
8dp  — Small (internal padding)
12dp — Medium-Small
16dp — Medium (standard padding)
20dp — Medium-Large
24dp — Large
32dp — XLarge (section spacing)
48dp — XXLarge (hero spacing)
64dp — XXXLarge (screen margins on tablet)
```

### Elevation

```
Level 0 — 0dp   (flat surfaces, backgrounds)
Level 1 — 1dp   (cards at rest)
Level 2 — 3dp   (cards on hover)
Level 3 — 6dp   (FAB at rest)
Level 4 — 8dp   (modals, dialogs)
Level 5 — 12dp  (bottom sheets)
```

### Icon Set

- **Library:** Material Symbols (Rounded style)
- **Size:** 24dp (standard), 20dp (dense), 32dp (hero icons)
- **Weight:** 400 (regular), 600 (active/selected)

---

## 🗂️ PROJECT STRUCTURE — CLEAN ARCHITECTURE

```
com.company.attendanceapp/
│
├── app/
│   ├── MainActivity.kt
│   ├── AttendanceApplication.kt
│   └── di/
│       ├── AppModule.kt
│       ├── NetworkModule.kt
│       ├── DatabaseModule.kt
│       └── RepositoryModule.kt
│
├── core/
│   ├── common/
│   │   ├── Resource.kt           (sealed class: Success/Error/Loading)
│   │   ├── UiState.kt
│   │   ├── Constants.kt
│   │   └── Extensions.kt
│   ├── network/
│   │   ├── GoogleSheetsApi.kt
│   │   ├── AuthInterceptor.kt
│   │   └── NetworkMonitor.kt
│   ├── location/
│   │   ├── LocationManager.kt
│   │   ├── GeofenceManager.kt
│   │   └── HaversineCalculator.kt
│   ├── security/
│   │   ├── EncryptedPreferences.kt
│   │   └── TokenManager.kt
│   └── utils/
│       ├── DateTimeUtils.kt
│       ├── TimezoneUtils.kt
│       └── ValidationUtils.kt
│
├── data/
│   ├── local/
│   │   ├── database/
│   │   │   ├── AttendanceDatabase.kt
│   │   │   ├── dao/
│   │   │   │   ├── EmployeeDao.kt
│   │   │   │   ├── AttendanceDao.kt
│   │   │   │   ├── ShiftDao.kt
│   │   │   │   └── SyncQueueDao.kt
│   │   │   └── entities/
│   │   │       ├── EmployeeEntity.kt
│   │   │       ├── AttendanceEntity.kt
│   │   │       ├── ShiftEntity.kt
│   │   │       └── SyncQueueEntity.kt
│   │   └── preferences/
│   │       └── UserPreferencesDataStore.kt
│   ├── remote/
│   │   ├── sheets/
│   │   │   ├── GoogleSheetsDataSource.kt
│   │   │   ├── SheetsMapper.kt
│   │   │   └── dto/
│   │   │       ├── EmployeeDto.kt
│   │   │       ├── AttendanceDto.kt
│   │   │       └── ShiftDto.kt
│   │   └── firebase/
│   │       ├── FCMService.kt
│   │       └── AuthDataSource.kt
│   └── repository/
│       ├── EmployeeRepositoryImpl.kt
│       ├── AttendanceRepositoryImpl.kt
│       └── ShiftRepositoryImpl.kt
│
├── domain/
│   ├── model/
│   │   ├── Employee.kt
│   │   ├── Attendance.kt
│   │   ├── Shift.kt
│   │   ├── Location.kt
│   │   └── AttendanceStatus.kt
│   ├── repository/
│   │   ├── EmployeeRepository.kt
│   │   ├── AttendanceRepository.kt
│   │   └── ShiftRepository.kt
│   └── usecase/
│       ├── attendance/
│       │   ├── CheckInUseCase.kt
│       │   ├── CheckOutUseCase.kt
│       │   ├── ValidateLocationUseCase.kt
│       │   └── CalculateWorkHoursUseCase.kt
│       ├── employee/
│       │   ├── GetEmployeeProfileUseCase.kt
│       │   └── UpdateEmployeeShiftUseCase.kt
│       └── reports/
│           ├── GetDailyReportUseCase.kt
│           └── GetMonthlyReportUseCase.kt
│
├── presentation/
│   ├── navigation/
│   │   ├── AppNavGraph.kt
│   │   ├── Screen.kt             (sealed class with routes)
│   │   └── BottomNavItem.kt
│   ├── theme/
│   │   ├── Theme.kt
│   │   ├── Color.kt
│   │   ├── Typography.kt
│   │   ├── Shape.kt
│   │   └── Spacing.kt
│   ├── components/               (shared reusable UI)
│   │   ├── cards/
│   │   ├── buttons/
│   │   ├── dialogs/
│   │   ├── inputs/
│   │   ├── charts/
│   │   └── maps/
│   └── screens/
│       ├── splash/
│       ├── auth/
│       ├── home/
│       ├── checkin/
│       ├── history/
│       ├── schedule/
│       ├── leave/
│       ├── reports/
│       ├── profile/
│       ├── notifications/
│       └── admin/
│
└── worker/
    ├── SyncAttendanceWorker.kt
    ├── DailySummaryWorker.kt
    ├── MissedCheckoutWorker.kt
    └── GeofenceWorker.kt
```

---

## 📱 SCREENS — COMPLETE UI SPECIFICATION

---

### SCREEN 1 — SPLASH SCREEN

**File:** `SplashScreen.kt`

**UI Layout:**
```
Background: Animated gradient — Primary (#1A73E8) → PrimaryContainer (#D3E3FD)
Center:
  - App Logo (SVG vector, 120×120dp, white tint)
    → Scale animation: 0.5 → 1.0 with spring effect (800ms)
  - App Name: "AttendX" (displaySmall, white, bold)
    → Fade-in after logo (delay: 400ms)
  - Tagline: "Smart Attendance. Real-time." (bodyMedium, white 80% alpha)
    → Fade-in (delay: 700ms)
Bottom:
  - Lottie loading animation (circular, white, 48dp, 1000ms delay)
  - "Powered by Google Workspace" (labelSmall, white 60% alpha)
```

**Logic:**
- Check authentication state via Firebase
- Check onboarding completion in DataStore
- Route to: Login / Onboarding / Home
- Minimum display duration: 2000ms

---

### SCREEN 2 — ONBOARDING FLOW (3 Pages)

**File:** `OnboardingScreen.kt`

**Layout:** HorizontalPager (swipeable) + static bottom controls

**Page 1 — Location Access:**
```
Illustration: Lottie animation — map pin dropping on office building (280dp)
Title: "Smart Location Check-In" (headlineMedium, OnSurface)
Body: "We verify your location when you check in to ensure accurate attendance records."
      (bodyLarge, OnSurface 70% alpha, center aligned)
```

**Page 2 — Custom Schedules:**
```
Illustration: Lottie — clock with calendar (280dp)
Title: "Your Schedule, Your Way" (headlineMedium)
Body: "Every employee has a custom shift. No more one-size-fits-all attendance."
```

**Page 3 — Real-time Sync:**
```
Illustration: Lottie — Google Sheets sync animation (280dp)
Title: "Always in Sync" (headlineMedium)
Body: "Attendance data syncs to Google Sheets instantly. Access it anywhere, anytime."
```

**Bottom Controls (persistent across pages):**
```
Row (horizontally spaced):
  Skip button (TextButton, labelLarge, Primary) — only on pages 1-2
  Page indicators (3 dots — active: 8dp Primary filled, inactive: 6dp Outline)
  Next / Get Started button (FilledButton, Primary, rounded 50dp)
    → "Next" on pages 1-2, "Get Started" on page 3
```

---

### SCREEN 3 — LOGIN / AUTHENTICATION

**File:** `LoginScreen.kt`

**Background:**
- Soft wave shape at top (Primary color, 35% of screen height)
- Card rises from bottom with 16dp rounding

**Header Area (on wave):**
```
Logo + App Name (centered, white)
Subtitle: "Sign in to your workspace" (bodyMedium, white 80%)
```

**Login Card (bottom 70% of screen):**
```
Card (Surface, elevation Level 3, cornerRadius 28dp top-only):

  Greeting: "Welcome Back 👋" (headlineMedium, OnSurface)
  Sub: "Enter your credentials to continue" (bodyMedium, OnSurface 60%)

  [16dp gap]

  OutlinedTextField: "Email Address"
    - Leading icon: email (Material Symbol)
    - Keyboard: EmailAddress
    - ImeAction: Next
    - Error state with shake animation

  [12dp gap]

  OutlinedTextField: "Password"
    - Leading icon: lock
    - Trailing icon: visibility toggle (eye)
    - Keyboard: Password
    - ImeAction: Done
    - Error state with inline error text below

  [8dp gap]

  "Forgot Password?" → TextButton (right-aligned, labelLarge, Primary)

  [16dp gap]

  FilledButton: "Sign In" (full width, 56dp height, 50dp radius)
    - Loading state: CircularProgressIndicator replaces text

  [12dp gap]

  Row: Divider — "OR" — Divider (bodySmall, Outline color)

  [12dp gap]

  OutlinedButton: Google Sign-In (full width, 56dp)
    - Google 'G' logo icon (left)
    - Text: "Continue with Google"

  [16dp gap]

  "Don't have an account? Contact Admin" (bodySmall, center)
    → "Contact Admin" is clickable (labelMedium, Primary)
```

**Animations:**
- Card slides up on enter (300ms, EaseOut)
- Form fields focus-ring animation (color transition 200ms)
- Button press: scale 0.97 (100ms spring)
- Error shake: 3 oscillations (400ms)

---

### SCREEN 4 — HOME / DASHBOARD

**File:** `HomeScreen.kt`

**Top App Bar (custom, non-scrolling):**
```
Background: Primary color
Height: 72dp (extended for visual richness)
Left: 
  - Avatar (40dp circle, Coil loaded, initials fallback)
  - Column:
    - "Good Morning, Riya 👋" (titleMedium, white)
    - Current date: "Monday, 22 Feb 2026" (bodySmall, white 80%)
Right:
  - IconButton: Notifications (bell icon, white)
    → Badge with unread count (red dot, 8dp, labelSmall white)
  - IconButton: More options (three dots)
```

**Body (LazyColumn):**

**1. Status Hero Card (full width, 16dp horizontal margin):**
```
Card (gradient: Primary → Secondary, cornerRadius 20dp, elevation 4dp):
  
  Top row:
    Left: "Today's Status" (labelMedium, white 80%)
    Right: StatusChip (PRESENT / ABSENT / etc.)
  
  Center:
    Large clock display (displayMedium, white, bold)
    → Ticking live clock (updates every second)
  
  Middle row (3 columns evenly spaced):
    Column 1: 
      Icon: login (white 80%)
      "Check In" (labelSmall, white 60%)
      Time value or "--:--" (titleMedium, white, bold)
    Column 2:
      Vertical divider (white 30%)
    Column 3:
      Icon: logout
      "Check Out" (labelSmall)
      Time value or "--:--"
    Column 4 (if checked in):
      Icon: timer
      "Duration" 
      "2h 34m" (live updating)

  Bottom: Check-In / Check-Out Button
    → If not checked in: 
        FilledButton "CHECK IN" (white bg, Primary text, full width, 52dp, 50dp radius)
    → If checked in:
        FilledButton "CHECK OUT" (ErrorContainer bg, Error text, full width, 52dp)
    → If already checked out:
        Disabled state + "Shift Complete ✓" text
```

**2. Shift Info Card:**
```
Card (Surface, elevation 1dp, cornerRadius 16dp, 16dp horizontal margin):
  Row:
    Left column:
      "Your Shift Today" (labelMedium, OnSurface 60%)
      Shift name: "Morning Shift" (titleMedium, OnSurface bold)
      "09:00 AM — 06:00 PM" (bodyMedium, Primary)
    Right:
      CircularProgressIndicator (large, 72dp):
        Progress = hours_worked / total_shift_hours
        Center text: "67%" (titleMedium, Primary)
        Label below: "Completed" (labelSmall)

  Divider (12dp vertical gap)
  
  3-column row (equally spaced):
    [Break Time] [Overtime] [Grace Period]
    Each: labelSmall (gray) + titleSmall (OnSurface bold) below
```

**3. Weekly Overview (horizontal bar chart):**
```
Card (Surface, 16dp margin):
  Header row:
    "This Week" (titleMedium, OnSurface)
    "View Details →" (labelMedium, Primary, TextButton)
  
  Vico ColumnChart:
    7 bars (Mon–Sun)
    Bar color: PRESENT=Secondary, LATE=Tertiary, ABSENT=Error, LEAVE=Tertiary container
    Bar height proportional to hours worked
    X-axis: Day labels (bodySmall)
    Y-axis: Hours (bodySmall)
    Touch: show tooltip with exact hours + status
```

**4. Quick Actions (2×2 grid):**
```
Row of 4 equally-spaced cards (88dp tall each):

  [📅 My Schedule] [📋 Apply Leave]
  [📊 Reports]     [📍 My Location]

Each card (Surface, cornerRadius 12dp, elevation 1dp):
  Icon (32dp, Primary)
  Label (labelMedium, OnSurface)
  → Ripple on tap
  → Navigate to respective screen
```

**5. Recent Activity (last 5 entries):**
```
"Recent Activity" header + "See All" link

LazyColumn items:
  Each row (AttendanceListItem):
    Leading: Date circle (40dp, PrimaryContainer bg)
      Month (labelSmall, Primary)
      Day (titleMedium, Primary, bold)
    Middle:
      Status badge chip (colored per status)
      "09:02 AM → 06:15 PM" (bodySmall, OnSurface 70%)
      Hours: "9h 13m" (labelMedium, Secondary)
    Trailing:
      Location icon (if flagged: Error color)
      Arrow icon

  Divider between items (OutlineVariant, 0.5dp)
```

**Bottom Navigation Bar:**
```
NavigationBar (Surface, elevation Level 2):
  5 tabs: Home | Check-In | History | Reports | Profile
  
  Each NavBarItem:
    Icon (filled when selected, outlined when not)
    Label (labelSmall)
    Selected indicator: pill-shaped background (PrimaryContainer)
    Animation: icon scale 0.85→1.0 + color transition (200ms)

  Center tab "Check-In":
    Extra-large FAB-style (Primary filled, 64dp circle, elevated above bar)
    Icon: QR code or fingerprint (white, 28dp)
    Slight elevation: Level 5
```

---

### SCREEN 5 — CHECK-IN / CHECK-OUT

**File:** `CheckInScreen.kt`

**Full Screen Layout (no bottom nav on this screen):**

**Top Section (40% of screen):**
```
Google Maps Compose (MapView):
  - Shows current device location with blue dot
  - Shows geofence circle (dashed border):
      → Inside zone: GREEN circle (#34A853, 20% fill alpha)
      → Outside zone: RED circle (#D93025, 20% fill alpha)
  - Office marker (custom icon: building emoji rendered on PrimaryContainer)
  - Camera auto-zooms to fit both user and geofence (padding: 80dp)
  - Non-interactive (no scroll/zoom gestures from this screen)
```

**Middle Panel (animated sliding card from bottom):**
```
Card (Surface, cornerRadius 28dp top-only, elevation Level 5):
  Handle bar (40×4dp, OutlineVariant, centered, 12dp top padding)

  Location Status Banner:
    → INSIDE ZONE:
        Row: CheckCircle icon (Secondary) + "You are inside the office zone" (bodyMedium, Secondary)
        Background: SecondaryContainer, cornerRadius 8dp
    → OUTSIDE ZONE:
        Row: ErrorOutline icon (Error) + "You are outside the permitted area" 
        Background: ErrorContainer
        Sub-text: "Distance: 247m from office" (bodySmall, Error)

  [16dp gap]

  "Current Location" section:
    labelMedium gray: "Detected Location"
    bodyMedium bold: "Tech Park, Bangalore, Karnataka"
    bodySmall gray: "Lat: 12.9716° N | Lng: 77.5946° E | Accuracy: ±12m"
    Last Updated timestamp (labelSmall, Outline)
    [Refresh icon button — fetches fresh GPS]

  [16dp gap]

  "Shift Information" row:
    Shift: "Morning 09:00–18:00" | Grace: "±10 min" | Status: chip
  
  [20dp gap]

  IF NOT CHECKED IN:
    FilledButton "MARK CHECK-IN" (full width, 56dp, 50dp radius, Secondary color)
      → Shows confirmation bottom sheet before confirming
  
  IF CHECKED IN:
    Row (two buttons):
      [OutlinedButton "BREAK" (48dp)] + [FilledButton "MARK CHECK-OUT" (48dp, Error)]
  
  IF OUTSIDE ZONE:
    Additional "Request Override" TextButton below main button
      → Opens reason submission dialog
  
  [12dp gap]
  
  "Selfie Check-In" optional toggle:
    Row: CameraIcon + "Enable selfie verification" + Switch
    → If enabled: opens CameraX capture before check-in completes
```

**Check-In Confirmation Bottom Sheet:**
```
BottomSheet (scrimColor 60% black, cornerRadius 28dp top):
  "Confirm Check-In" (headlineSmall, center)
  [divider]
  
  Summary card (SurfaceVariant, cornerRadius 12dp):
    Row: Time       | "09:03 AM" (bold, Secondary)
    Row: Date       | "Monday, 22 Feb 2026"
    Row: Location   | "Office – Tech Park"
    Row: Status     | StatusChip "ON TIME" / "LATE (+3 min)"
    Row: Coordinates| "12.9716°N, 77.5946°E"
  
  [16dp gap]
  
  Row (full width, spaced):
    TextButton "Cancel" (Error)
    FilledButton "Confirm Check-In" (Secondary, 50dp radius, flex)
  
  Animation: checkmark Lottie plays on success (1500ms), then dismiss
```

**Success Overlay (post check-in):**
```
Full screen overlay (Secondary color, 90% alpha):
  Lottie: large animated checkmark (200dp, white)
  "Checked In Successfully!" (headlineMedium, white, bold)
  Time: "09:03 AM" (displaySmall, white)
  "Have a great day! 🌟" (bodyLarge, white 80%)
  → Auto-dismiss after 2500ms
  → Haptic feedback: success pattern
```

---

### SCREEN 6 — ATTENDANCE HISTORY

**File:** `HistoryScreen.kt`

**Top App Bar:**
```
"Attendance History" (titleLarge)
Actions: [Filter icon] [Download icon]
```

**Month Selector (horizontal scroll, non-scrollable LazyRow):**
```
Row of MonthChips:
  Each chip: "Feb 2026" (FilterChip, labelMedium)
  Selected: Primary filled
  Scroll 6 months back, 0 months forward
```

**Summary Stats Row (below month selector):**
```
4 stat cards (horizontal scroll, LazyRow, 80dp tall each):
  [Present: 18] [Late: 2] [Absent: 1] [Leave: 1]
  Each: icon (24dp) + count (displaySmall, bold) + label (labelSmall)
  Color-coded borders matching status colors
```

**Calendar View (toggleable with List View):**
```
Toggle tabs: "Calendar" | "List" (TabRow, Primary indicator)

CALENDAR VIEW:
  Material3 CalendarView (custom Compose impl):
    Each day cell (40×40dp):
      Day number (bodyMedium)
      Color dot below:
        Present → Secondary
        Late → Tertiary  
        Absent → Error
        Leave → Purple
        Holiday → Pink
        Future → no dot
    Tap on day → shows DayDetailBottomSheet

LIST VIEW (LazyColumn):
  Grouped by week ("Week 1 — Feb 1–7"):
    AttendanceListCard (each item):
      Left: Day + Date column (44dp wide)
      Center:
        Status chip (rounded, labeled)
        Time: "09:00 AM → 06:12 PM" (bodyMedium)
        Duration: "9h 12m worked" (bodySmall, Secondary)
        [if late]: "Late by 12 min" (labelSmall, Tertiary)
        [if out-of-zone]: Location icon (Error) + "Location flagged"
      Right: 
        Chevron (expand for details)
      
      Expanded state (AnimatedVisibility):
        Map thumbnail (120dp tall, read-only, shows check-in location)
        Device info (labelSmall, gray)
        Notes if any (bodySmall, italic)
```

**Day Detail Bottom Sheet (on calendar tap):**
```
Title: "Monday, 22 Feb 2026" (titleLarge)
Large status chip (center)
Timeline (vertical):
  ○ Check-In: 09:02 AM (SecondaryContainer bubble)
      "Office – Tech Park | ±8m accuracy"
  │ (vertical line, dashed)
  ○ Break Start: 01:00 PM (TertiaryContainer)
  │
  ○ Break End: 01:35 PM
  │
  ○ Check-Out: 06:15 PM (ErrorContainer if early, SecondaryContainer if on time)
Summary:
  Total: 9h 13m | Break: 35m | Net: 8h 38m | OT: 0h
```

---

### SCREEN 7 — MY SCHEDULE

**File:** `ScheduleScreen.kt`

**Layout:**
```
Top: "My Schedule" (titleLarge) + current week display

Week Selector:
  ← Prev Week | "Feb 17–23, 2026" (titleMedium center) | Next Week →
  (Arrows: IconButton, Primary)

Daily Schedule Cards (LazyColumn):
  For each day (Mon–Sun):
    Card (Surface, cornerRadius 12dp, 16dp margin):
      
      Header row:
        Day + Date (titleSmall, bold)
        [TODAY badge if applicable] (FilledChip, PrimaryContainer)
        Status chip (right-aligned)
      
      If Working Day:
        Timeline bar (linear, horizontal):
          Filled segment = work hours
          Color coded: Primary for standard, Tertiary for OT, Error for absent
        
        Info row (3 columns):
          "Start: 09:00" | "End: 18:00" | "Break: 60m"
        
        Progress row:
          "8/9 hours" LinearProgressIndicator (Secondary, 8dp tall, cornerRadius 4dp)
      
      If Holiday / Leave:
        Centered: Icon + "National Holiday" or "Annual Leave – Approved"
        Background: PastelVariant of respective status color
      
      If Future Day:
        Grayed out, dashed border, "Scheduled: 09:00–18:00" (Outline color)
```

**Shift Details Bottom Sheet (tap any card):**
```
Shift Name: "Morning Shift – A" (headlineSmall)
Chip: shift type (Fixed / Flexible / Rotating)

Details grid:
  Start Time       | 09:00 AM
  End Time         | 06:00 PM
  Break Duration   | 60 minutes
  Grace Period     | 10 minutes
  Min Hours        | 8 hours
  Overtime After   | 9 hours
  Working Days     | Mon Tue Wed Thu Fri
  Location(s)      | Tech Park HQ, Client Site B

"Request Shift Change" TextButton (Primary, bottom)
```

---

### SCREEN 8 — LEAVE MANAGEMENT

**File:** `LeaveScreen.kt`

**Tabs:** "My Leaves" | "Apply Leave" | "Leave Balance"

**Tab 1 — My Leaves:**
```
Filter chips (horizontal scroll):
  All | Pending | Approved | Rejected | Cancelled

LeaveRequestCard (each):
  Left color bar (4dp wide — Pending:Amber, Approved:Green, Rejected:Red)
  Content:
    Leave type badge chip (Annual / Sick / Casual / WFH)
    Date range: "Feb 25, 2026 – Feb 27, 2026" (titleSmall, bold)
    "3 Days" (labelMedium, Secondary)
    Reason: "Family function" (bodySmall, OnSurface 70%, max 2 lines)
  Trailing:
    Status chip + Date submitted (labelSmall, gray)
  Tap → LeaveDetailBottomSheet
```

**Tab 2 — Apply Leave:**
```
Form Card (Surface, 16dp padding, 16dp margin):

  "Leave Type" DropdownMenu:
    Options: Annual Leave / Sick Leave / Casual Leave / WFH / Compensatory
    Selected item shows in ExposedDropdownMenuBox

  "Date Range" picker:
    Row: [From Date Field] [→] [To Date Field]
    Both: OutlinedTextField + CalendarIcon
    Tap: opens DateRangePicker dialog (Material3)
    Auto-calculates: "3 working days selected"

  "Half Day" toggle:
    SwitchRow: "Apply for half day?" → if enabled, show Morning/Afternoon selector

  "Reason" OutlinedTextField:
    Multiline (4 lines), maxChars: 300
    Character counter bottom-right: "47/300"

  "Attach Medical Certificate" (for Sick Leave only):
    OutlinedButton + AttachFile icon
    → Opens file picker (PDF/JPG support)
    → Shows attachment chip with filename + remove (×) option

  Leave Balance Info (below form, SurfaceVariant card):
    Available: 12 days | Used: 6 days | Pending: 2 days

  Submit FilledButton (full width, 56dp, Primary)
    → Shows confirmation dialog before submitting
```

**Tab 3 — Leave Balance:**
```
Circular progress cards (2-column grid):

  Each card (Surface, cornerRadius 16dp):
    CircularProgressIndicator (72dp, colored):
      Track = OutlineVariant
      Indicator = leave type color
    Center: "12" (displaySmall, bold)
    Label: "Annual Leave" (labelMedium)
    Sub: "6 used / 12 total" (bodySmall, gray)
```

---

### SCREEN 9 — REPORTS & ANALYTICS

**File:** `ReportsScreen.kt`

**Date Range Selector:**
```
Row (Surface card, 16dp padding):
  "This Month" selected by default
  Chip group: Today | This Week | This Month | Custom
  → Custom: DateRangePicker dialog
```

**Summary Cards (2×2 grid, lazy grid):**
```
Card 1: Total Working Days — "22 days" (displaySmall, Primary)
Card 2: Present Days — "19 days" (Secondary)
Card 3: Late Arrivals — "3 times" (Tertiary)
Card 4: Overtime Hours — "7.5 hrs" (purple)

Each card:
  Icon (32dp, colored) + stat (displayMedium bold) + label (bodySmall gray)
  Trend arrow (↑↓) with percentage vs last month (labelSmall)
```

**Charts Section:**

```
1. Attendance Trend (Line Chart — Vico):
   X-axis: Dates of month
   Y-axis: Hours worked
   Line: Primary color, filled area gradient (Primary 30%)
   Points: 6dp circles, clickable (shows tooltip)

2. Status Distribution (Donut Chart):
   Segments: Present/Late/Absent/Leave (color coded)
   Center: Total days count (displaySmall bold)
   Legend below chart (dots + labels + percentages)

3. Weekly Hours Bar Chart:
   Grouped bars per week
   Each bar: standard hours (Primary) + overtime (Tertiary) stacked
   Target line: dashed (Error color at required hours)

4. Check-In Time Scatter (bubble chart):
   X: Days, Y: Time (6AM–12PM range)
   Bubble color: On time = Green, Late = Amber
   Trend line to show consistency
```

**Export Options (bottom of screen):**
```
Row of 3 OutlinedButtons:
  [📄 Export PDF] [📊 Export Excel] [📋 View in Sheets]
  → Tap → shows export progress dialog → share intent
```

---

### SCREEN 10 — NOTIFICATIONS

**File:** `NotificationsScreen.kt`

**Top Bar:** "Notifications" + "Mark All Read" (TextButton, right)

**Filter Tabs:** All | Alerts | Approvals | Reminders | System

**Notification List (LazyColumn):**
```
NotificationItem:
  Leading: 
    CircleAvatar (40dp):
      Color = notification category color
      Icon (Material Symbol, white, 20dp)
  Content:
    Title (titleSmall, bold — unread) / (bodyMedium — read)
    Body (bodySmall, OnSurface 60%, max 2 lines)
    Timestamp (labelSmall, Outline)
  Trailing (for action notifications):
    Row of 2 small buttons: [Approve] [Reject] or [View]

  Unread: SurfaceVariant background + 4dp Primary left border
  Read: Surface background
  Long press: shows dismiss option

Categories & Icons:
  ⚠️ ALERT (red)    — Out-of-zone, suspicious activity
  ✅ APPROVAL (green) — Leave approved, override granted
  🕐 REMINDER (blue) — Shift starting, missed checkout
  ⚙️ SYSTEM (gray)   — App update, data sync complete
```

---

### SCREEN 11 — PROFILE

**File:** `ProfileScreen.kt`

**Header (Primary gradient background, 200dp tall):**
```
CircleAvatar (96dp, white border 3dp):
  Profile photo (Coil) or initials fallback (Primary color)
Edit overlay (bottom-right of avatar, 28dp circle, white bg, Primary icon)

Employee Name (headlineMedium, white, bold)
Employee ID + Department (bodyMedium, white 80%)
Status chip: "Active Employee" (SecondaryContainer, Secondary text)
```

**Profile Cards (LazyColumn):**

**Section 1 — Personal Info:**
```
Card (Surface, cornerRadius 16dp):
  Header: "Personal Information" (titleMedium, bold) + Edit IconButton

  InfoRow × multiple:
    Leading icon (20dp, Primary) | Label (bodySmall gray) | Value (bodyMedium bold)
    
    Examples:
    📧 Email    | riya.sharma@company.com
    📱 Phone    | +91 98765 43210
    🏢 Dept     | Engineering – Mobile
    👤 Role     | Senior Developer
    📍 Location | Tech Park HQ, Bangalore
    👨‍💼 Manager  | Arjun Mehta
    📅 Joined   | January 15, 2022
```

**Section 2 — Shift Configuration:**
```
Card (Surface, cornerRadius 16dp):
  "My Shift" header + "Request Change" TextButton

  Shift name badge (PrimaryContainer chip)
  Grid:
    Shift Type | Fixed
    Start Time | 09:00 AM
    End Time   | 06:00 PM
    Work Days  | Mon–Fri
    Break      | 60 min
    Grace      | 10 min
    OT Threshold | 9 hrs
```

**Section 3 — App Settings:**
```
Card (Surface):
  SettingRow items (each: icon + label + trailing control):
  
  🔔 Push Notifications    → Switch (on/off)
  🌙 Dark Mode             → Switch
  🌐 Language              → "English" + ChevronRight
  📍 Location Permission   → "Always" chip + settings shortcut
  🔐 Change Password       → ChevronRight
  📊 Data Sync             → "Last synced: 2 min ago" + Sync button
  🗑️ Clear Cache           → ChevronRight (shows storage used)
```

**Logout Button (bottom, full width):**
```
OutlinedButton (Error color border + text, 56dp, 50dp radius):
  "Sign Out"
→ Shows confirmation dialog before signing out
```

---

### SCREEN 12 — ADMIN PANEL (Role-based, Admins Only)

**File:** `AdminDashboardScreen.kt`

**Access:** Only rendered if user.role == ADMIN or HR

**Navigation Drawer (from Admin bottom tab):**
```
DrawerHeader (Primary gradient):
  Avatar + "Admin Panel" label + user name

DrawerItems:
  📊 Dashboard
  👥 Employees
  🗺️ Live Map
  📅 All Schedules
  📋 All Leave Requests
  ⚠️ Alerts & Flags
  📈 Analytics
  ⚙️ Settings
```

**Admin Dashboard Screen:**
```
Stats Overview (LazyVerticalGrid 2 columns):
  Total Employees | Present Today | Absent | On Leave
  Late Arrivals   | Out-of-Zone  | Overtime Running | Pending Approvals

Live Attendance Feed (LazyColumn, auto-refreshing every 30s):
  Each row: Avatar + Name + Status chip + Time + Location indicator
  Color-coded left border by status

Alert Panel (Amber card if alerts exist):
  "3 employees flagged today" → View button
  Swipeable list of alerts
```

**Employee Management Screen:**
```
SearchBar (full width, 56dp, rounded)
Filter chips: All | Active | Inactive | Field Workers | Remote

EmployeeCard (each):
  Avatar + Name + ID + Dept + Status
  Today's attendance inline
  Long press: quick actions (Edit / Deactivate / Message)

FAB: "Add Employee" (+) → opens AddEmployeeBottomSheet
```

**Live Map Screen:**
```
Full-screen Google Maps:
  Custom markers for each checked-in employee:
    Circle with initials (color = status)
    Tap → info bubble: Name + Check-in time + Zone status
  Geofence circles for each office location (green dashed)
  
  Bottom sheet (collapsed by default, draggable):
    "24 employees checked in" heading
    LazyColumn of employees with location status
    Filter: [In Zone] [Out of Zone] [Not Checked In]
```

---

## 🗃️ GOOGLE SHEETS DATABASE SCHEMA

### Sheet 1 — `employees_master`
```
A: employee_id | B: name | C: email | D: phone
E: department  | F: role | G: manager_id | H: shift_id
I: location_id | J: remote_allowed | K: probation_flag
L: status (ACTIVE/INACTIVE) | M: profile_photo_url | N: created_at
```

### Sheet 2 — `shifts_config`
```
A: shift_id | B: shift_name | C: shift_type (FIXED/FLEXIBLE/ROTATING/SPLIT)
D: start_time | E: end_time | F: break_minutes | G: working_days (CSV: MON,TUE...)
H: grace_minutes | I: flexible_flag | J: min_hours | K: overtime_threshold_hours
L: night_shift_flag | M: timezone | N: created_at
```

### Sheet 3 — `locations_master`
```
A: location_id | B: location_name | C: latitude | D: longitude
E: radius_meters | F: address | G: city | H: timezone
I: location_type (OFFICE/HOME/CLIENT/FIELD) | J: active_flag
```

### Sheet 4 — `attendance_log`
```
A: log_id | B: employee_id | C: date | D: check_in_time | E: check_out_time
F: check_in_lat | G: check_in_lng | H: check_out_lat | I: check_out_lng
J: check_in_accuracy | K: location_status (VALID/OUT_OF_ZONE/SUSPICIOUS)
L: hours_worked | M: break_minutes | N: overtime_hours | O: late_minutes
P: early_departure_minutes | Q: status (PRESENT/LATE/ABSENT/HALF_DAY)
R: device_id | S: ip_address | T: selfie_url | U: notes | V: override_approved_by
W: sync_status | X: created_at | Y: updated_at
```

### Sheet 5 — `leave_requests`
```
A: leave_id | B: employee_id | C: leave_type | D: from_date | E: to_date
F: half_day_flag | G: half_day_period (MORNING/AFTERNOON) | H: reason
I: attachment_url | J: status (PENDING/APPROVED/REJECTED/CANCELLED)
K: applied_at | L: approved_by | M: approved_at | N: rejection_reason
```

### Sheet 6 — `alerts_log`
```
A: alert_id | B: employee_id | C: alert_type | D: message
E: severity (LOW/MEDIUM/HIGH) | F: timestamp | G: resolved_flag
H: resolved_by | I: resolved_at
```

---

## ⚙️ CORE BUSINESS LOGIC — KOTLIN IMPLEMENTATION

### Haversine Geofence Calculator

```kotlin
object HaversineCalculator {
    private const val EARTH_RADIUS_METERS = 6_371_000.0

    fun distanceBetween(
        lat1: Double, lng1: Double,
        lat2: Double, lng2: Double
    ): Double {
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)
        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(lat1)) *
                cos(Math.toRadians(lat2)) *
                sin(dLng / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return EARTH_RADIUS_METERS * c
    }

    fun isInsideGeofence(
        userLat: Double, userLng: Double,
        zoneLat: Double, zoneLng: Double,
        radiusMeters: Double,
        gpsAccuracy: Float
    ): GeofenceResult {
        val distance = distanceBetween(userLat, userLng, zoneLat, zoneLng)
        val effectiveRadius = radiusMeters + gpsAccuracy // account for GPS error
        return GeofenceResult(
            isInside = distance <= effectiveRadius,
            distanceFromCenter = distance,
            distanceFromBoundary = distance - radiusMeters
        )
    }
}

data class GeofenceResult(
    val isInside: Boolean,
    val distanceFromCenter: Double,
    val distanceFromBoundary: Double
)
```

### Shift Calculation Engine

```kotlin
class ShiftCalculationEngine {

    fun calculateAttendance(
        shift: Shift,
        checkInTime: LocalDateTime,
        checkOutTime: LocalDateTime?
    ): AttendanceCalculation {
        val shiftStart = shift.startTime.atDate(checkInTime.toLocalDate())
        val shiftEnd = shift.endTime.atDate(checkInTime.toLocalDate())
            .let { if (shift.isNightShift && shift.endTime < shift.startTime) 
                   it.plusDays(1) else it }

        val lateMinutes = maxOf(0L, 
            ChronoUnit.MINUTES.between(
                shiftStart.plusMinutes(shift.gracePeriodMinutes.toLong()), 
                checkInTime
            )
        )

        val hoursWorked = checkOutTime?.let { 
            ChronoUnit.MINUTES.between(checkInTime, it).toDouble() / 60 
                - (shift.breakMinutes.toDouble() / 60)
        } ?: 0.0

        val overtimeHours = maxOf(0.0, hoursWorked - shift.overtimeThresholdHours)

        val earlyDepartureMinutes = checkOutTime?.let {
            maxOf(0L, ChronoUnit.MINUTES.between(it, shiftEnd))
        } ?: 0L

        val status = when {
            checkOutTime == null -> AttendanceStatus.INCOMPLETE
            hoursWorked >= shift.minHours / 2 && hoursWorked < shift.minHours -> AttendanceStatus.HALF_DAY
            lateMinutes > 0 -> AttendanceStatus.LATE
            else -> AttendanceStatus.PRESENT
        }

        return AttendanceCalculation(
            hoursWorked = hoursWorked,
            lateMinutes = lateMinutes.toInt(),
            overtimeHours = overtimeHours,
            earlyDepartureMinutes = earlyDepartureMinutes.toInt(),
            status = status
        )
    }
}
```

### Google Sheets API Integration

```kotlin
interface GoogleSheetsApi {
    
    @GET("v4/spreadsheets/{spreadsheetId}/values/{range}")
    suspend fun getValues(
        @Path("spreadsheetId") spreadsheetId: String,
        @Path("range") range: String,
        @Header("Authorization") token: String
    ): SheetValuesResponse

    @PUT("v4/spreadsheets/{spreadsheetId}/values/{range}")
    suspend fun updateValues(
        @Path("spreadsheetId") spreadsheetId: String,
        @Path("range") range: String,
        @Query("valueInputOption") valueInputOption: String = "USER_ENTERED",
        @Header("Authorization") token: String,
        @Body body: UpdateValuesRequest
    ): UpdateValuesResponse

    @POST("v4/spreadsheets/{spreadsheetId}/values/{range}:append")
    suspend fun appendValues(
        @Path("spreadsheetId") spreadsheetId: String,
        @Path("range") range: String,
        @Query("valueInputOption") valueInputOption: String = "USER_ENTERED",
        @Query("insertDataOption") insertDataOption: String = "INSERT_ROWS",
        @Header("Authorization") token: String,
        @Body body: AppendValuesRequest
    ): AppendValuesResponse

    @POST("v4/spreadsheets/{spreadsheetId}/values:batchUpdate")
    suspend fun batchUpdate(
        @Path("spreadsheetId") spreadsheetId: String,
        @Header("Authorization") token: String,
        @Body body: BatchUpdateRequest
    ): BatchUpdateResponse
}
```

---

## 🔒 PERMISSIONS CONFIGURATION

### AndroidManifest.xml

```xml
<!-- Location -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
<uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION"/>

<!-- Network -->
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

<!-- Camera (selfie check-in) -->
<uses-permission android:name="android.permission.CAMERA"/>

<!-- Notifications -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>
<uses-permission android:name="android.permission.VIBRATE"/>

<!-- Foreground Service (location tracking) -->
<uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_LOCATION"/>

<!-- Receive Boot (restart geofence after reboot) -->
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
```

---

## 🔔 NOTIFICATION CHANNELS & TRIGGERS

| Channel ID | Name | Importance | Trigger |
|---|---|---|---|
| `attendance_alerts` | Attendance Alerts | HIGH | Late check-in, out-of-zone |
| `shift_reminders` | Shift Reminders | DEFAULT | 30min before shift start |
| `leave_approvals` | Leave Updates | DEFAULT | Approval/Rejection |
| `missed_checkout` | Missed Check-Out | HIGH | 2hrs after shift end |
| `sync_status` | Sync Status | LOW | Background data sync |

---

## ⚡ WORKMANAGER JOBS

```kotlin
// Periodic attendance sync (every 15 minutes when app backgrounded)
PeriodicWorkRequestBuilder<SyncAttendanceWorker>(15, TimeUnit.MINUTES)
    .setConstraints(Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build())
    .build()

// Daily summary at 11:59 PM
OneTimeWorkRequestBuilder<DailySummaryWorker>()
    .setInitialDelay(millisUntilMidnight, TimeUnit.MILLISECONDS)
    .build()

// Missed check-out checker (runs at shift_end + 2 hours for each employee)
OneTimeWorkRequestBuilder<MissedCheckoutWorker>()
    .setInputData(workDataOf("employee_id" to employeeId))
    .setInitialDelay(twoHoursAfterShift, TimeUnit.MILLISECONDS)
    .build()
```

---

## 🛡️ SECURITY SPECIFICATIONS

| Layer | Implementation |
|---|---|
| Auth Tokens | Encrypted DataStore (AES-256 via EncryptedSharedPreferences) |
| API Keys | BuildConfig (never hardcoded) + Secrets Gradle Plugin |
| Biometric | BiometricPrompt API for app unlock |
| Root Detection | SafetyNet / Play Integrity API check on launch |
| Certificate Pinning | OkHttp CertificatePinner for API calls |
| Buddy Punch Detection | Compare device_id + IP per employee per shift |
| Session Management | Auto-logout after 8 hours of inactivity |
| RBAC | Role stored server-side, verified per API call |
| Data in Transit | HTTPS/TLS 1.3 enforced via Network Security Config |

---

## 📐 RESPONSIVE DESIGN RULES

```
Phone Portrait  (< 600dp) → Single column, full-width cards, bottom nav
Phone Landscape (600–840dp) → 2-column grids, side-by-side panels
Tablet Portrait (840–1200dp) → Navigation Rail replaces bottom nav
Tablet Landscape (> 1200dp) → Permanent Navigation Drawer, master-detail layout

WindowSizeClass usage:
  COMPACT  → Phone portrait layout
  MEDIUM   → Tablet portrait layout  
  EXPANDED → Tablet landscape / foldable layout

Foldable support:
  Use FoldingFeature API to detect fold state
  Avoid placing interactive elements in hinge area
```

---

## 🧪 TESTING STRATEGY

```kotlin
// Unit Test: ShiftCalculationEngine
@Test
fun `check-in 12 minutes late should return LATE status with lateMinutes = 12`() {
    val result = engine.calculateAttendance(
        shift = morningShift,
        checkInTime = LocalDateTime.of(2026, 2, 22, 9, 12),
        checkOutTime = LocalDateTime.of(2026, 2, 22, 18, 0)
    )
    assertEquals(AttendanceStatus.LATE, result.status)
    assertEquals(2, result.lateMinutes) // 2 min after grace period of 10
}

// UI Test: CheckInButton
@Test
fun checkInButton_isDisabledWhenOutsideGeofence() {
    composeTestRule.setContent { CheckInScreen(isInsideZone = false) }
    composeTestRule.onNodeWithTag("check_in_button").assertIsNotEnabled()
}
```

---

## 🚀 DEPLOYMENT CONFIGURATION

### Build Variants

```kotlin
// build.gradle.kts
buildTypes {
    debug {
        applicationIdSuffix = ".debug"
        versionNameSuffix = "-DEBUG"
        isDebuggable = true
        buildConfigField("String", "SHEETS_BASE_URL", "\"https://sheets.googleapis.com/\"")
    }
    release {
        isMinifyEnabled = true
        isShrinkResources = true
        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        signingConfig = signingConfigs.getByName("release")
    }
}

flavorDimensions += "environment"
productFlavors {
    create("staging") { applicationIdSuffix = ".staging" }
    create("production") { /* clean */ }
}
```

### ProGuard Rules

```
# Keep Google Sheets API models
-keep class com.company.attendance.data.remote.dto.** { *; }
# Keep Retrofit interfaces
-keep,allowobfuscation interface * { @retrofit2.http.* <methods>; }
# Keep Moshi adapters
-keepclassmembers class * { @com.squareup.moshi.* <fields>; <methods>; }
# Keep Room entities
-keep @androidx.room.Entity class *
```

---

## 📈 SCALABILITY RECOMMENDATIONS

| Scale | Strategy |
|---|---|
| 1–50 employees | Single Google Sheet, direct API calls, no caching needed |
| 50–500 employees | Add Redis-style caching via Room, batch API writes every 5 min |
| 500–2,000 employees | Shard data across multiple Sheets by department, add Firebase RTDB for live status |
| 2,000–10,000 employees | Migrate to Cloud Firestore (keep Sheets as reporting layer only), implement CDN for profile images |
| 10,000+ employees | Full backend migration (Node.js / Go microservices), Sheets as export-only sink |

---

## 📋 DELIVERABLES CHECKLIST

```
✅ Complete Kotlin project with Gradle KTS
✅ All 12+ screens fully implemented in Jetpack Compose
✅ Material Design 3 theme with dark mode
✅ Google Sheets CRUD operations (append/read/update/batch)
✅ FusedLocationProvider + Geofencing with Haversine
✅ Firebase Auth (Google Sign-In + Email/Password)
✅ Room Database for offline support + sync queue
✅ WorkManager background jobs (sync, reminders, alerts)
✅ FCM Push Notifications (all channels)
✅ Role-based access control (Employee / HR / Admin)
✅ Admin Panel with live map view
✅ Attendance reports with Vico charts
✅ Leave management (apply, approve, balance)
✅ Biometric app unlock
✅ ProGuard / R8 optimized release build
✅ Unit tests + UI tests (>80% coverage target)
✅ Responsive layout (phone + tablet + foldable)
✅ Accessibility (TalkBack, min 48dp touch targets, content descriptions)
```

---

> **Tip:** After pasting this prompt, follow up with targeted commands like:
> - *"Implement the CheckInScreen.kt with full Compose UI"*
> - *"Write the GoogleSheetsDataSource.kt with all CRUD functions"*
> - *"Generate the ShiftCalculationEngine with all edge cases"*
> - *"Build the AdminDashboard composable with live employee map"*
> - *"Set up the complete Hilt DI module structure"*
