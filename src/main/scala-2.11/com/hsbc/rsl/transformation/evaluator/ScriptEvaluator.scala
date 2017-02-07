package com.hsbc.rsl.transformation.evaluator

import javax.script._

import scala.io.Source
/**
  * Created by smit22 on 1/24/2017.
  */
object ScriptEvaluator extends Serializable{

  private val ENGINE_NAME = "nashorn"

  private val scriptEngineManager :  ScriptEngineManager = new ScriptEngineManager()
  private val scriptEngine : ScriptEngine = scriptEngineManager.getEngineByName(ENGINE_NAME)
  private val scriptContext : ScriptContext = new SimpleScriptContext()
  private val engineBindings : Bindings = scriptContext.getBindings(ScriptContext.ENGINE_SCOPE)
  private val compilingEngine : Compilable = scriptEngine.asInstanceOf[Compilable]
  private val commonFunctions : (String) => List[String] = (fileName : String) => Source.fromFile(fileName).getLines.toList


  def preEvaluate(contextVariables : List[(String,Any)]) : Unit = {
    if(engineBindings.size() != 0) engineBindings.clear()
    for(contextTuple <- contextVariables) engineBindings.put(contextTuple._1, contextTuple._2)
    commonFunctions("D:\\Data\\RSL\\DataTransformation\\L3\\CommonFunctions.js").foreach(x => compilingEngine.compile(x).eval(scriptContext).toString)
  }

  @throws(classOf[ScriptException])
  def evaluate(key : String, expression : String) : String = {
    val evaluatedValue = Option(expression) match {
      case Some(exp) if exp.trim.equalsIgnoreCase("null") => ""
      case Some(exp) if !exp.trim.isEmpty => compilingEngine.compile(exp).eval (scriptContext).toString
      case _ => ""
    }
    engineBindings.put(key,evaluatedValue)
    evaluatedValue
  }

  def postEvaluate() : Unit = engineBindings.clear()
}