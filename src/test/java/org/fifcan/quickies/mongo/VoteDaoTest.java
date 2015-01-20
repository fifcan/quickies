package org.fifcan.quickies.mongo;

import org.fifcan.quickies.WebMvcConfiguration;
import org.fifcan.quickies.data.UserGroupSession;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebMvcConfiguration.class)
public class VoteDaoTest {

    @Autowired
    VoteDao voteDao;

    @Test
    public void getTopFutureSessions() throws Exception {
        List<UserGroupSession> sessions = voteDao.getTopFutureSessions(3);
        Assert.assertFalse(sessions.isEmpty());
    }

    @Test
    public void getTopPastSessions() throws Exception {
        List<UserGroupSession> sessions = voteDao.getTopPastSessions(3);
        Assert.assertFalse(sessions.isEmpty());
    }
}