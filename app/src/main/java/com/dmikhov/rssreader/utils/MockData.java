package com.dmikhov.rssreader.utils;

import com.dmikhov.rssreader.models.RssItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmikhov on 27.01.2017.
 */
public class MockData {

    public static List<RssItem> getRssItems() {
        List<RssItem> rssItems = new ArrayList<>();
        RssItem item1 = new RssItem("Saturday's Best Deals: Thinkpad Yoga, TRX Training System, LED Bulbs, and More",
                "https://i.kinja-img.com/gawker-media/image/upload/s--8h4E5YRu--/c_fit,fl_progressive,q_80,w_636/ptcjy7zyrdtbvdrfzcde.jpg",
                "<img src=\"https://i.kinja-img.com/gawker-media/image/upload/s--8h4E5YRu--/c_fit,fl_progressive,q_80,w_636/ptcjy7zyrdtbvdrfzcde.jpg\" /><p>A <a rel=\"nofollow\" data-amazonasin=\"B01MU8QODW\" data-amazonsubtag=\"[p|1791729567[a|B01MU8QODW[au|5876237249236602398[b|deals\" onclick=\"window.ga(&#39;send&#39;, &#39;event&#39;, &#39;Commerce&#39;, &#39;deals - Saturday\\&#39;s Best Deals: Thinkpad Yoga, TRX Training System, LED Bulbs, and More&#39;, &#39;B01MU8QODW&#39;);\" data-amazontag=\"kotakuamzn-20\" href=\"https://www.amazon.com/Lenovo-Generation-Touchscreen-Convertible-Ultrabook/dp/B01MU8QODW/ref=gbps_img_s-3_bb19_004ad627?smid=A1KWJVS57NX03I&amp;pf_rd_p=41fd713f-6bfe-4299-a021-d2b94872bb19&amp;pf_rd_s=slot-3&amp;pf_rd_t=701&amp;pf_rd_i=gb_main&amp;pf_rd_m=ATVPDKIKX0DER&amp;pf_rd_r=YQSGAJJ5R9RPZXYHJ0NP&amp;tag=kotakuamzn-20&amp;ascsubtag=[t|link[p|1791729567[a|B01MU8QODW[au|5876237249236602398[b|deals\">Lenovo Thinkpad Yoga</a> for under $300, a <a rel=\"nofollow\" data-amazonasin=\"B00AFENHNI\" data-amazonsubtag=\"[p|1791729567[a|B00AFENHNI[au|5876237249236602398[b|deals\" onclick=\"window.ga(&#39;send&#39;, &#39;event&#39;, &#39;Commerce&#39;, &#39;deals - Saturday\\&#39;s Best Deals: Thinkpad Yoga, TRX Training System, LED Bulbs, and More&#39;, &#39;B00AFENHNI&#39;);\" data-amazontag=\"kotakuamzn-20\" href=\"https://www.amazon.com/TRX-Suspension-Training-Home-Gym/dp/B00AFENHNI/ref=gbps_img_s-3_bb19_cd6e2aff?smid=A1KWJVS57NX03I&amp;pf_rd_p=41fd713f-6bfe-4299-a021-d2b94872bb19&amp;pf_rd_s=slot-3&amp;pf_rd_t=701&amp;pf_rd_i=gb_main&amp;pf_rd_m=ATVPDKIKX0DER&amp;pf_rd_r=YQSGAJJ5R9RPZXYHJ0NP&amp;tag=kotakuamzn-20&amp;ascsubtag=[t|link[p|1791729567[a|B00AFENHNI[au|5876237249236602398[b|deals\">TRX suspension system</a>, cheap <a rel=\"nofollow\" data-amazonasin=\"B01BFCGBN6\" data-amazonsubtag=\"[p|1791729567[a|B01BFCGBN6[au|5876237249236602398[b|deals\" onclick=\"window.ga(&#39;send&#39;, &#39;event&#39;, &#39;Commerce&#39;, &#39;deals - Saturday\\&#39;s Best Deals: Thinkpad Yoga, TRX Training System, LED Bulbs, and More&#39;, &#39;B01BFCGBN6&#39;);\" data-amazontag=\"kotakuamzn-20\" href=\"https://www.amazon.com/TCP-LA927KND6-Equivalent-Non-Dimmable-Light/dp/B01BFCGBN6/ref=gbps_img_s-3_bb19_4e18bd97?smid=ATVPDKIKX0DER&amp;pf_rd_p=41fd713f-6bfe-4299-a021-d2b94872bb19&amp;pf_rd_s=slot-3&amp;pf_rd_t=701&amp;pf_rd_i=gb_main&amp;pf_rd_m=ATVPDKIKX0DER&amp;pf_rd_r=34YYVSX4H8C18P6HWZJX&amp;tag=kotakuamzn-20&amp;ascsubtag=[t|link[p|1791729567[a|B01BFCGBN6[au|5876237249236602398[b|deals\">LED light bulbs</a>, and more lead Saturday’s best deals.<br></p><p><a href=\"http://deals.kinja.com/saturdays-best-deals-thinkpad-yoga-trx-training-syste-1791729567\">Read more...</a></p>",
                1485532282167L);
        RssItem item2 = new RssItem("Title 2", "http://i.kinja-img.com/gawker-media/image/upload/s--D341uu7---/c_scale,f_auto,fl_progressive,q_80,w_800/vzihm90dwjntaagdujkn.png", "Description 2", 1485532282167L);
        RssItem item3 = new RssItem("Title 3", "http://weknowyourdreams.com/images/rock/rock-08.jpg", "Description 3", 1485532282167L);
        RssItem item4 = new RssItem("Title 4", "", "Description 4", 1485431282167L);
        rssItems.add(item1);
        rssItems.add(item2);
        rssItems.add(item3);
        rssItems.add(item4);
        return rssItems;
    }

    public static List<RssItem> getRssItemsUpdated() {
        List<RssItem> rssItems = new ArrayList<>();
        RssItem item1 = new RssItem("Title 1", "http://i.kinja-img.com/gawker-media/image/upload/s--D341uu7---/c_scale,f_auto,fl_progressive,q_80,w_800/vzihm90dwjntaagdujkn.png", "Description 1", 1485532282167L);
        RssItem item2 = new RssItem("Title 2", "http://i.kinja-img.com/gawker-media/image/upload/s--D341uu7---/c_scale,f_auto,fl_progressive,q_80,w_800/vzihm90dwjntaagdujkn.png", "Description 2", 1485532282167L);
        RssItem item3 = new RssItem("Title 3", "http://i.kinja-img.com/gawker-media/image/upload/s--D341uu7---/c_scale,f_auto,fl_progressive,q_80,w_800/vzihm90dwjntaagdujkn.png", "Description 3", 1485532282167L);
        RssItem item4 = new RssItem("Title 4", "", "Description 4", 1485431282167L);
        RssItem item5 = new RssItem("Title 5", "", "Description 5", 1465431284167L);
        rssItems.add(item1);
        rssItems.add(item2);
        rssItems.add(item3);
        rssItems.add(item4);
        rssItems.add(item5);
        return rssItems;
    }

}
