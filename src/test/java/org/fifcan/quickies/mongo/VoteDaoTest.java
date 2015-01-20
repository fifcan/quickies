package org.fifcan.quickies.mongo;

import org.fifcan.quickies.WebMvcConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebMvcConfiguration.class)
public class VoteDaoTest {

    @Autowired
    VoteDao voteDao;

    @Test
    public void getTopFutureSessions() throws Exception {
        voteDao.getTopFutureSessions(3);
    }

    @Test
    public void getTopPastSessions() throws Exception {
        voteDao.getTopPastSessions(3);
    }
}