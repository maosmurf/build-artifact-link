package jenkins.plugins.buildartifactlink;

import java.io.IOException;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Action;
import hudson.model.BuildListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;
import hudson.util.FormValidation;

import org.kohsuke.stapler.AncestorInPath;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

/**
 * @author xza
 */
public class BuildArtifactLink extends Notifier
{
    static final boolean ERROR_IF_NOT_EXIST = true;
    static final boolean EXPECTING_FILE = true;
    public static final String ARTIFACT = "artifact/";

    private final String icon;
    private final String url;
    private final String title;

    @DataBoundConstructor
    public BuildArtifactLink(final String icon, final String url, final String title)
    {
        this.icon = icon;
        this.url = url;
        this.title = title;
    }

    public String getIcon()
    {
        return icon;
    }

    public String getTitle()
    {
        return title;
    }

    public String getUrl()
    {
        return url;
    }

    @Override
    public boolean perform(final AbstractBuild<?, ?> build, final Launcher launcher,
        final BuildListener listener) throws InterruptedException, IOException
    {
        final String artifactUrl = ARTIFACT + url;
        final Action action = new BuildArtifactLinkAction(title, icon, artifactUrl);
        build.addAction(action);
        return true;
    }

    public BuildStepMonitor getRequiredMonitorService()
    {
        return BuildStepMonitor.NONE;
    }

    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Publisher>
    {

        @Override
        public boolean isApplicable(final Class<? extends AbstractProject> jobType)
        {
            return true;
        }

        @Override
        public String getDisplayName()
        {
            return Messages.BuildArtifactLink_DisplayName();
        }

        public FormValidation doCheckUrl(@AncestorInPath AbstractProject project, @QueryParameter String aUrl)
            throws IOException
        {
            final FilePath ws = project.getSomeWorkspace();
            if (ws != null)
            {
                return ws.validateRelativePath(aUrl, ERROR_IF_NOT_EXIST, EXPECTING_FILE);

            }
            return FormValidation.warning(hudson.model.Messages.AbstractProject_NoWorkspace());
        }
    }
}
