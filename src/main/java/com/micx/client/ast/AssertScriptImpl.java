/**
 * AssertEqualsImpl.java
 * Created on  7/4/2016 7:37 PM
 * modify on                user            modify content
 * 7/4/2016 7:37 PM        micx
 * <p/>
 * Dianping.com Inc.
 * Copyright (c) 2003-2014 All Rights Reserved.
 */

package com.micx.client.ast;

import com.micx.client.ast.entity.AssertFailedMsg;
import com.micx.client.ast.entity.AssertGroup;
import com.micx.client.ast.entity.AssertScript;
import com.micx.client.ast.entity.AssertStatement;
import com.micx.client.ast.enums.AssertLogic;
import com.micx.client.ast.iface.AssertIface;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

import static com.micx.client.ast.enums.AssertType.SCRIPT;

/**
 * Created by micx  on 2016/04/07 7:37 PM.
 */
public class AssertScriptImpl implements AssertIface<AssertScript> {
    public boolean assertResponse(AssertScript expected, String actual, List<AssertFailedMsg> astResults) {
        if (expected == null || CollectionUtils.isEmpty(expected.getAssertGroups())){
            AssertFailedMsg assertFailedMsg = new AssertFailedMsg(SCRIPT, expected, actual, false);
            astResults.add(assertFailedMsg);
            return false;
        }
        List<AssertGroup> groups = expected.getAssertGroups();
        int result = 1;
        for (AssertGroup group: groups) {
            List<AssertStatement> statements = group.getStatements();
            int groupResult = -1;
            for (AssertStatement statement : statements) {
                int tmp = 0;
                switch (statement.getType()) {
                    case EQUALS:
                        tmp = statement.getContent().toLowerCase().equals(actual.toLowerCase()) ? 1 : 0;
                        break;
                    case CONTAINS:
                        tmp = actual.toLowerCase().contains(statement.getContent().toLowerCase()) ? 1 : 0;
                        break;
                }
                if (groupResult == -1){
                    groupResult = tmp;
                }else {
                    if (group.getAssertLogic() == AssertLogic.AND) {
                        groupResult = groupResult & tmp;
                    } else if (group.getAssertLogic() == AssertLogic.OR) {
                        groupResult = groupResult | tmp;
                    }
                }
                if (tmp > 0) {
                    statement.getResult().incrementAndGet();
                }
            }
            if (groupResult > 0){
                group.getResult().incrementAndGet();
            }
            result = result & groupResult;
        }
        if (result == 0){
            AssertFailedMsg assertFailedMsg = new AssertFailedMsg(SCRIPT, expected, actual, result>0);
            astResults.add(assertFailedMsg);
        }
        return result > 0;
    }
}
