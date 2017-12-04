package com.example.milosevi.rxjavatest.details.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by milosevi on 10/11/17.
 */

public class Review {
    ///
//    {
//        "id":"58d04679c3a3682dcd0002c6", "author":"Salt-and-Limes", "content":
//        "**Spoilers**\r\n\r\nThe live action remake of \"Beauty and the Beast\" was good, but it failed to capture the magic of the cartoon version. There were somethings that they got right, and others that dragged on.\r\n\r\nI thought \"Be Our Guest\" was done beautifully. The 3d made it even more enchanting. The main characters' backstories also added some depth to them. However, there were some scenes that I felt added nothing to the story. Such as the search for Belle by Gaston and her father. The \"No one is like Gaston\" scene didn't have the bravado or arrogance of the original.\r\n\r\nI also felt that Luke Evans was miscast. He wasn't the handsomest guy in town, nor was he the strongest. Which is why it was hard for me to accept him as the character. Emma Watson was serviceable. Her voice was fine, but it wasn't strong enough to carry Belle's songs. Dan Stevens was the best part of the film. I felt that he should have had more songs, because he has a beautiful baritone. Although his beast costume should have been more frightening. \r\n\r\nOverall, it's a fun film to watch. Though, I wouldn't call it a classic.", "url":
//        "https://www.themoviedb.org/review/58d04679c3a3682dcd0002c6"
//    }
//,
//
//    {
//        "id":"58e3b31892514127f6020406", "author":"Gimly", "content":
//        "Disney's done a great job with Gaston and The Beast, the two aspects that I always thought would be the most important, and the most difficult, to nail.\r\n\r\n_Final rating:★★★ - I personally recommend you give it a go._", "url":
//        "https://www.themoviedb.org/review/58e3b31892514127f6020406"
//    },
//
//    {
//        "id":"594309d5c3a3686c0d01406e", "author":"Reno", "content":
//        "**Only beauty would conquer the beast!**\r\n\r\nThis is not the first time screen adaptation, that means every one of us have seen either version of the films according to what generation we belong. Mine was the animated one and then there was a modern-day version called 'Beastly'. Not to forget there's a recent French version made on the big scale that I yet to watch. So it's not about the story at all. It is about the quality of film, performances, and particularly how the modern technology used to narrate the story.\r\n\r\nLike the recent Disney's live-actions 'Cinderella', 'The BFG', 'The Jungle Book' and many more, this is another excellently adapted film from the book. Though visually it tried to be true to the original, and that's absolutely great thinking. Because in this modern world, we all try something new out of the original contents for sometimes to mess it up.\r\n\r\nExcellent casting, the beauty was the best pick among all. The graphics were awesome. The CGI beast was flawless, yet that usual dark and cloudy atmosphere helped it to be so perfect. Since it is listed under Musical, I was worried about the songs. I truly don't like modern Musicals. But this film was not completely filled with songs. Yes, there were like half a dozen, but good ones. Retained the songs from the 1991 film, and in addition three new were composed.\r\n\r\nI did not think the Disney would get away with consecutive successes. Great achievement, and their attempts were very precautionary on their every step. Now this is Disney's second highest grossed film ever and first in live-action. The film was for all ages unlike old Disney films. That's the best thing about the modern Disney films that they have adopted. Definitely recommended for at least once watch it.\r\n\r\n_8/10_", "url":
//        "https://www.themoviedb.org/review/594309d5c3a3686c0d01406e"
//    },
//
//    {
//        "id":"5958a657c3a36828e701d4dc", "author":"Marypsa", "content":
//        "JUST INCREDIBLE\r\n\r\n> I fell in love with that movie!The actors performed fantastically and the effects were so well-maid.It is a classic movie.The best Beauty and the Beast version!!The alive castle was so magical,the effects so realistic and the characters so sympathetic...It was just PERFECT!", "url":
//        "https://www.themoviedb.org/review/5958a657c3a36828e701d4dc"
//    }]
//            ,"total_pages":1,"total_results":4}

    ///
    @SerializedName("id")
    private String id;//id

    @SerializedName("author")
    private String mName;//author
    @SerializedName("content")
    private String mContent;//key

    @SerializedName("url")
    private String mUrl;//release_date

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return mName;
    }


    public String getContent() {
        return mContent;
    }


    public String getUrl() {
        return mUrl;
    }


    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", mName='" + mName + '\'' +
                ", mContent='" + mContent + '\'' +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }

    public Review() {
    }

    public Review(String id, String name, String mContent, String mUrl) {
        this.id = id;
        this.mName = name;
        this.mContent = mContent;
        this.mUrl = mUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String mName) {
        this.mName = mName;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
