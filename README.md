# Unreal Text Engine

### *Unreal Text Engine* - The most (maybe) advanced engine for creating text games!

---
# English

A lightweight and extensible console-based engine for building text adventure games (RPGs) in Java. This project was developed as a deep dive into software architecture, design patterns, data-driven game development, and just for fun)))

---

## 🚀 Key Features

* **🗄️ Data-Driven Design**: The entire game world—including locations, transitions, items, and enemies—is defined in an external `JSON` file. This allows you to modify the plot and game balance without changing the source code.
* **🧩 Reflection-based Event System**: Features dynamic loading of game scripts. You can implement new gameplay events (traps, quest triggers, etc.) by simply adding new classes to the `events` package. The engine automatically detects and instantiates them by name from the JSON config.
* **⚙️ Behavioral State Machine**: The game loop is managed via distinct states (`EXPLORING`, `IN_COMBAT`, `IN_INVENTORY`), ensuring clean transition logic and a stable gameplay flow.
* **⚔️ Combat & Inventory Systems**: Includes turn-based combat mechanics, weapon equipment support, and consumable items with polymorphic behavior.

---

## 🛠 Tech Stack

* **Language**: Java 21+
* **Data Format**: JSON (Google Gson library)
* **Principles**: OOP, SOLID, Command Pattern, Strategy Pattern, Reflection API.

---

## 📂 Project Structure

* `core/` — Core engine logic: game loop, system managers, and world loading.
* `events/` — Package for custom game scripts and event logic.
* `resources/` — Configuration files (e.g., `world.json`).

---

## 💬 Feedback & Contribution
This project was created for educational purposes. I am actively exploring game architecture and welcome any constructive criticism, refactoring tips, or Pull Requests.

Feel free to suggest improvements or contribute to the code!

---
# Русский
Кроссплатформенный консольный движок для создания текстовых приключенческих игр (RPG) на Java. Проект разработан в процессе изучения архитектуры ПО и паттернов проектирования и по приколу)))

---

## 🚀 Ключевые особенности

* **🗄️ Data-Driven дизайн**: Весь игровой мир (локации, переходы, предметы, монстры) описывается в внешнем `JSON` файле. Это позволяет менять сюжет и баланс игры, не трогая исходный код.
* **🧩 Система событий на Reflection**: Реализована динамическая загрузка игровых скриптов. Можно создавать новые события (ловушки, сюжетные триггеры), просто добавляя классы в пакет `events`. Движок подхватит их автоматически по имени из JSON.
* **⚙️ Поведенческая стейт-машина**: Управление игровым циклом через состояния (`EXPLORING`, `IN_COMBAT`, `IN_INVENTORY`), что обеспечивает четкую логику переходов и стабильность игрового процесса.
* **⚔️ Боевая система и Инвентарь**: Реализована механика пошагового боя, поддержка экипировки оружия и использования расходных предметов с полиморфным поведением.

---

## 🛠 Технологический стек

* **Язык**: Java 21+
* **Формат данных**: JSON (библиотека Google Gson)
* **Принципы**: OOP, SOLID, Command Pattern, Strategy Pattern.

---

## 📂 Структура проекта

* `core/` — Ядро движка: игровой цикл, менеджеры систем, загрузка мира.
* `events/` — Пакет для игровых скриптов и ивентов.
* `resources/` — Конфигурационные файлы мира (`world.json`).

---

## 💬 Обратная связь
Проект создан в учебных целях. Я активно изучаю архитектуру и буду рад любой конструктивной критике, советам по рефакторингу или Pull Request-ам.

---
Not affiliated with Epic Games
