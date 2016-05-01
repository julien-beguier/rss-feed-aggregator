package fr.valentinpinilla.library;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

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
        return entries;
    }

    @SuppressWarnings("unchecked")
	public SyndFeed agregate(String[] urls) {
        try {
            for (int i = 0; i < urls.length; i++) {
                URL inputUrl = new URL(urls[i]);

                SyndFeedInput input = new SyndFeedInput();
                SyndFeed inFeed = input.build(new XmlReader(inputUrl));

                sortEntriesByDate(inFeed.getEntries());
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

    public void sortEntriesByDate(List<SyndEntry> newList){

        boolean added = false;

        for (int i = 0; i < newList.size(); i++){
            added = false;
            for (int ii = 0; ii < entries.size(); ii++) {
                if (newList.get(i).getPublishedDate().compareTo(entries.get(ii).getPublishedDate()) > 0) {
                    entries.add(ii, newList.get(i));
                    added = true;
                    break;
                }
            }
            if (!added)
                entries.add(newList.get(i));
        }
    }

    public void displayContent() {
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
        for (Iterator<?> entryIter = feed.getEntries().iterator(); entryIter.hasNext(); ) {
            SyndEntry syndEntry = (SyndEntry) entryIter.next();
            System.out.println(syndEntry.getDescription().getValue());
            System.out.println("--------------------------------");
        }
    }

    public void displayDate(){
        for (Iterator<?> entryIter = feed.getEntries().iterator(); entryIter.hasNext(); ) {
            SyndEntry syndEntry = (SyndEntry) entryIter.next();
            System.out.println(syndEntry.getPublishedDate());
            System.out.println("--------------------------------");
        }
    }
}