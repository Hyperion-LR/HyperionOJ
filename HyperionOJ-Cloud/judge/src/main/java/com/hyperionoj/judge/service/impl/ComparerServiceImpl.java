package com.hyperionoj.judge.service.impl;

import com.hyperionoj.judge.config.FilePath;
import com.hyperionoj.judge.service.ComparerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @author Hyperion
 * @date 2021/12/8
 */
@Service
public class ComparerServiceImpl implements ComparerService {

    @Resource
    private FilePath filePath;

    /**
     * 比较运行结果是否正确
     *
     * @param codeRes   代码运行的结果
     * @param problemId 题目id
     * @return 代码运行是否正确
     */
    @Override
    public Boolean compare(String codeRes, String problemId, Integer index) {
        String outDir = filePath.getProblem() + File.separator + problemId + File.separator + "out" + File.separator + "out" + index + ".txt";
        StringBuilder ans = new StringBuilder();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(outDir)))) {
            String tmp;
            while ((tmp = fileReader.readLine()) != null) {
                ans.append(tmp).append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String codeAns = ans.toString();
        codeRes = StringUtils.remove(codeRes, '\n');
        codeAns = StringUtils.remove(codeAns, '\n');
        return StringUtils.compare(codeRes, codeAns) == 0;
    }
}
