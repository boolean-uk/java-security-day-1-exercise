````mermaid
erDiagram
    VideoGame{
        int id PK
        string title
        string developer
        string publisher
        int year
        string rating
        string genre
        string platform
    }
    User{
        int id PK
        string name
        string email
        string phone
    }
    Loan{
        int id PK
        User user
        VideoGame videoGame
        Date borrowedAt
        Date dueBy
        bool returned
    }
    
    User ||--o{ Loan : ""
    VideoGame ||--|| Loan : ""
````