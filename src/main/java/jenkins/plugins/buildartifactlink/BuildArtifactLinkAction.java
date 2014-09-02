package jenkins.plugins.buildartifactlink;

import hudson.model.Action;

/**
 * @author xza
 */
public class BuildArtifactLinkAction implements Action
{
    private final String displayName;
    private final String iconFileName;
    private final String urlName;

    public BuildArtifactLinkAction(final String displayName, final String iconFileName, final String urlName)
    {
        this.displayName = displayName;
        this.iconFileName = iconFileName;
        this.urlName = urlName;
    }

    @Override
    public String getDisplayName()
    {
        return displayName;
    }

    @Override
    public String getIconFileName()
    {
        return iconFileName;
    }

    @Override
    public String getUrlName()
    {
        return urlName;
    }
}
