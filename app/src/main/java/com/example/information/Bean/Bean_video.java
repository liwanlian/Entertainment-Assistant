package com.example.information.Bean;

import org.json.JSONObject;

import java.util.List;

import javax.security.auth.Subject;

//正在热映的bean数据  top100的bean数据  即将上映的bean数据
public class Bean_video {
    private int count;
    private int start;
    private int total;
    private  List<Subjectdata> subjects;
    private String  title;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Subjectdata> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subjectdata> subjects) {
        this.subjects = subjects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class Subjectdata{
        private ratingdata rating;
        private String[] genres;
        private String title;
        private List<castsdata> casts;
        private String[] durations;
        private int collect_count;
        private String mainland_pubdate;
        private boolean has_video;
        private String original_title;
        private String subtype;
        private List<castsdata> directors;
        private String[] pubdates;
        private String year;
        private castsdata.avatarsdata images;
        private String alt;
        private String id;

        public ratingdata getRating() {
            return rating;
        }

        public void setRating(ratingdata rating) {
            this.rating = rating;
        }

        public String[] getGenres() {
            return genres;
        }

        public void setGenres(String[] genres) {
            this.genres = genres;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<castsdata> getCasts() {
            return casts;
        }

        public void setCasts(List<castsdata> casts) {
            this.casts = casts;
        }

        public String[] getDurations() {
            return durations;
        }

        public void setDurations(String[] durations) {
            this.durations = durations;
        }

        public int getCollect_count() {
            return collect_count;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public String getMainland_pubdate() {
            return mainland_pubdate;
        }

        public void setMainland_pubdate(String mainland_pubdate) {
            this.mainland_pubdate = mainland_pubdate;
        }

        public boolean isHas_video() {
            return has_video;
        }

        public void setHas_video(boolean has_video) {
            this.has_video = has_video;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public List<castsdata> getDirectors() {
            return directors;
        }

        public void setDirectors(List<castsdata> directors) {
            this.directors = directors;
        }

        public String[] getPubdates() {
            return pubdates;
        }

        public void setPubdates(String[] pubdates) {
            this.pubdates = pubdates;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public castsdata.avatarsdata getImages() {
            return images;
        }

        public void setImages(castsdata.avatarsdata images) {
            this.images = images;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class ratingdata{
          private int max;
          private double average;
          private JSONObject details;
          private String stars;
          private int min;

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public double getAverage() {
                return average;
            }

            public void setAverage(double average) {
                this.average = average;
            }

            public JSONObject getDetails() {
                return details;
            }

            public void setDetails(JSONObject details) {
                this.details = details;
            }

            public String getStars() {
                return stars;
            }

            public void setStars(String stars) {
                this.stars = stars;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }
        }

        public static class castsdata{
            private avatarsdata avatars;
            private String name_en;
            private String name;
            private String alt;
            private String id;

            public avatarsdata getAvatars() {
                return avatars;
            }

            public void setAvatars(avatarsdata avatars) {
                this.avatars = avatars;
            }

            public String getName_en() {
                return name_en;
            }

            public void setName_en(String name_en) {
                this.name_en = name_en;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public static class avatarsdata{
                private String small;
                private String large;
                private String medium;

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }
            }
        }


    }
}
