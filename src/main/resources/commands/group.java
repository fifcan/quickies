package commands;

import org.crsh.cli.Command;
import org.crsh.cli.Option;
import org.crsh.cli.Usage;
import org.crsh.command.BaseCommand;
import org.fifcan.quickies.data.UserGroup;
import org.fifcan.quickies.mongo.SessionDao;
import org.fifcan.quickies.mongo.UserGroupDao;
import org.springframework.beans.factory.BeanFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by deft on 26/01/2015.
 */
@Usage("Quickie telnet commands to list JUG and Sessions :)")
public class group extends BaseCommand {

    @Command
    public List<String> list() {

        BeanFactory factory = getBeanFactory();

        UserGroupDao groupDao = factory.getBean(UserGroupDao.class);

        int i=1;
        List<String> groups = new LinkedList<String>();

        for (UserGroup group : groupDao.listGroups()) {
            groups.add(i + "#" + group.getName());
            i++;
        }
        return groups;
    }


    @Command
    public List<String> sessions(@Usage("GroupIdx")
                                 @Option(names={"gid","groupid"})
                                 int gid) {

        BeanFactory factory = getBeanFactory();

        UserGroupDao groupDao = factory.getBean(UserGroupDao.class);

        UserGroup group = groupDao.listGroups().get(gid - 1);

        SessionDao sessionDao = factory.getBean(SessionDao.class);

        return sessionDao.findSessionsByGroupId(group.getId()).stream()
                .map(s -> s.getName() + " / " + s.getEventDate())
                .collect(Collectors.toList())
                ;

    }


    private BeanFactory getBeanFactory() {
        return ((BeanFactory)context.getAttributes().get("spring.beanfactory"));
    }
}
