package fr.valentinpinilla.library;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * Created by Valentin on 17/04/2016.
 */
public class FluxRss {

    SyndFeed feed;
    List<SyndEntry> entries;

    public FluxRss(String url) {
        try {
            URL feedUrl = new URL(url);

            SyndFeedInput input = new SyndFeedInput();
            feed = input.build(new XmlReader(feedUrl));
            getAllEntries();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    public SyndFeed getFeed() {
        return feed;
    }

    @SuppressWarnings("unchecked")
	public List<SyndEntry> getAllEntries() {
        entries = feed.getEntries();
//        System.out.println("entries size = " + entries.size());
        return entries;
    }

    @SuppressWarnings("unchecked")
	public SyndFeed aggregate(String[] urls) {
        try {
            System.out.println("urls[] size = " + urls.length);
            for (int i = 0; i < urls.length; i++) {
                System.out.println("urls[" + i + "] = " + urls[i]);
                URL inputUrl = new URL(urls[i]);

                SyndFeedInput input = new SyndFeedInput();
                SyndFeed inFeed = input.build(new XmlReader(inputUrl));

//                System.out.println("inFeed.getEntries.size() = " + inFeed.getEntries().size());

                feed.getEntries().addAll(inFeed.getEntries());
            }
            return feed;

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("ERROR: " + ex.getMessage());
        }
        return null;
    }

    public void readFlux() {
        System.out.println(feed);
    }

    public void displayContent() {
//        System.out.println("entries size dans displayContent = " + entries.size());
        for (Iterator<?> entryIter = feed.getEntries().iterator(); entryIter.hasNext(); ) {
            SyndEntry syndEntry = (SyndEntry) entryIter.next();

            if (syndEntry.getContents() != null) {
                for (Iterator<?> it = syndEntry.getContents().iterator(); it.hasNext(); ) {
                    SyndContent syndContent = (SyndContent) it.next();

                    String value = syndContent.getValue();
                    System.out.println(value);
                    System.out.println("--------------------------------");
                }
            }
        }
    }

    public void displayDescription() {
//        System.out.println("entries size dans displayContent = " + entries.size());
        for (Iterator<?> entryIter = feed.getEntries().iterator(); entryIter.hasNext(); ) {
            SyndEntry syndEntry = (SyndEntry) entryIter.next();
            System.out.println(syndEntry.getDescription().getValue());
            System.out.println("--------------------------------");
        }
    }
}
