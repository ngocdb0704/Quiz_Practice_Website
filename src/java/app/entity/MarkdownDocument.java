package app.entity;

import java.util.Map;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.BulletList;
import org.commonmark.node.Document;
import org.commonmark.node.Heading;
import org.commonmark.node.Link;
import org.commonmark.node.ListBlock;
import org.commonmark.node.ListItem;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

public class MarkdownDocument {
    private Node documentNode;
    private static Parser parser;
    private static HtmlRenderer renderer;

    // well all this complexity because of the library I am using
    private static class HeadingVisitor extends AbstractVisitor {
        private ListBlock rootList = new BulletList();
        private int counter = 0;
        private String url;

        public HeadingVisitor(String url) {
            this.url = url;
        }

        private String getHeadingText(Heading heading) {
            // we assume the heading only has one text node as the child
            Node firstChild = heading.getFirstChild();
            if (firstChild == null) return null;

            String headingText = null;
            if (firstChild instanceof Text) {
                Text text = (Text)firstChild;
                headingText = text.getLiteral();
            }

            return headingText;
        }


        private ListBlock findListBlockParentByLevel(int level) {
            ListBlock root = rootList;
            ListBlock listBlock = rootList;

            int i = 1;
            while (i < level) {
                ListItem listItem = (ListItem)root.getLastChild();

                if (listItem == null) break;

                // can i go down one level? -- is the last element a block list?
                if (listItem.getLastChild() instanceof ListBlock) {
                    // yes, go down and save
                    i++;
                    listBlock = (ListBlock)listItem.getLastChild();
                    root = listBlock;
                } else {
                    // no, create and return
                    listBlock = new BulletList();
                    listItem.appendChild(listBlock);
                    return listBlock;
                }
            }

            return listBlock;
        }

        @Override
        public void visit(Heading heading) {
            String headingText = getHeadingText(heading);
            if (headingText == null) return;

            ListBlock parent = findListBlockParentByLevel(heading.getLevel());
            ListItem listItem = new ListItem();

            Link link = new Link(String.format("%s#heading-0%d", url, counter++), null);
            link.appendChild(new Text(headingText));

            listItem.appendChild(link);

            parent.appendChild(listItem);
        }

        @Override
        protected void visitChildren(Node parent) {
            // do not visit children
            return;
        }

        public ListBlock getList() {
            return rootList;
        }
    }

    private static class HeadingIdProvider implements AttributeProvider {
        int counter = 0;
        
        @Override
        public void setAttributes(Node node, String string, Map<String, String> attrs) {
            // we are dealing with headings right below the root of the document
            // so the parent must be the document
            if (node instanceof Heading && node.getParent() instanceof Document) {
                attrs.put("id", String.format("heading-0%d", counter));
                counter++;
            }
        }
    }

    /**
     * Initialize the renderer and parser once                           
     * Not the greatest solution, the parser and renderer will always be 
     * initialized and kept alive for the lifetime of the application
     * regardless of whether this class is being used or not. 
     * Lazy-loading is a possible solution but more complicated and not needed for now.
     */
    static {
        parser = Parser.builder().build();
        renderer = HtmlRenderer
                .builder()
                .attributeProviderFactory(new AttributeProviderFactory() {
                    @Override
                    public AttributeProvider create(AttributeProviderContext apc) {
                        return new HeadingIdProvider();
                    }
                })
                .build();
    }

    public MarkdownDocument(String documentText) {
        setText(documentText);
    }

    public void setText(String documentText) {
        documentNode = parser.parse(documentText);
    }

    public String toHtml() {
        return renderer.render(documentNode);
    }

    /**
     * Constructs a tree of all level headings 1 level down from the document root.
     * Returns the unordered list HTML
     * This does not handle headings inside of lists.
     * @return A HTML unordered list representing the tree.
     */
    public String getHtmlTableOfContents(String url) {
        HeadingVisitor visitor = new HeadingVisitor(url);
        Node child = documentNode.getFirstChild();

        while (child != null) {
            child.accept(visitor);
            child = child.getNext();
        }
        
        return renderer.render(visitor.getList());
    }
}
