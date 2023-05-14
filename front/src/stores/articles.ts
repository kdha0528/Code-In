import { defineStore } from 'pinia';
import type {Article} from "@/components/custom-types/article";
import type {Intro} from "@/components/custom-types/intro";
import type {Activity} from "@/components/custom-types/activity";

export const useArticlesStore = defineStore("article", {
    state: () => ({
        articles: [] as Article[] | Activity[],
        intro: {
          category: ' ',
          message: ' ',
        } as Intro,
        maxPage: 1,
    }),
    getters: {
        getArticles: (state) => {
            return state.articles;
        },
        getMaxPage: (state)=>{
            return state.maxPage;
        },
        getIntro: (state)=>{
            return state.intro;
        },
        introIsNull: (state) =>{
            return state.intro == null;
        }
    },
    actions: {
        addArticle(a: Article & Activity){
            this.articles.push(a);
        },
        setMaxPage(m: number){
            this.maxPage = m;
        },
        setIntro(intro: Intro){
            this.intro = intro;
        },
        clean(){
            this.articles = [];
            this.maxPage = 1;
        },
    },
    persist: {
        storage: sessionStorage,
    },
})
